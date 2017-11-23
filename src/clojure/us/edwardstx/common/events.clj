(ns us.edwardstx.common.events
  (:require [us.edwardstx.common.rabbitmq :refer [get-channel] :as rabbitmq]
            [us.edwardstx.common.uuid     :refer [uuid]]
            [clj-time.coerce              :as coerce]
            [clj-time.core                :as time]
            [cheshire.core                :as json]
            [langohr.consumers            :as lc]
            [langohr.queue                :as lq]
            [langohr.basic                :as lb]
            [langohr.core                 :as rmq]
            [manifold.stream              :as s]
            [manifold.deferred            :as d]
            [clojure.tools.logging        :as log]
            [com.stuartsierra.component   :as component]))

(defn publish-event-handler [service-name rabbitmq {:keys [body key id]}]
  (let [routing-key (format "events.%s.%s" service-name key)
        payload (json/generate-string body)
        channel (get-channel rabbitmq)]
    (try
      (lb/publish channel "events" routing-key payload {:content-type "application/json"
                                                        :message-id id
                                                        :timestamp (coerce/to-date (time/now))
                                                        :app-id service-name
                                                        :type key})
      (catch Exception ex #(log/error ex "Unable to publis event"))
      (finally (rmq/close channel)))))

(defn convert-payload [payload content-type]
  (let [payload (String. payload)]
    (if (= "application/json" content-type)
      (json/parse-string payload true)
      payload)))

(defn publish-event [{:keys [event-stream]} key body]
  (let [mid (uuid)]
    (s/put! event-stream {:key key :body body :id mid})
    mid))

(defn rcv-msg [stream ch {:keys [delivery-tag content-type] :as meta} ^bytes payload]
  (let [body (convert-payload payload content-type)]
    (->
     (s/put! stream (assoc meta :body body))
     (d/chain #(if % (lb/ack ch delivery-tag))))))

(defn event-subscription [{:keys [rabbitmq conf]} key]
  (let [channel (get-channel rabbitmq)
        event-stream (s/stream)
        queue-name (format "%s.%s" key (uuid))]
    (s/on-closed event-stream #(rmq/close channel))
    (lq/declare channel queue-name {:exclusive true :auto-delete true})
    (lq/bind channel queue-name "events" {:routing-key key})
    (lc/subscribe channel queue-name (partial rcv-msg event-stream) {:auto-ack false})
    event-stream))

(defrecord Events [conf rabbitmq event-stream]
  component/Lifecycle

  (start [this]
    (let [service-name (-> conf :conf :service-name)
          event-stream (s/stream)]
      (s/consume (partial publish-event-handler service-name rabbitmq) event-stream)
      (assoc this :event-stream event-stream)))

  (stop [this]
    (do
      (s/close! event-stream)
      (assoc this :channel nil :event-stream nil)))
  )

(defn new-events []
  (component/using
   (map->Events {})
   [:conf :rabbitmq]))

