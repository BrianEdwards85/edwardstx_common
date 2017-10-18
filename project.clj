(defproject us.edwardstx/edwardstx_common "1.0.2-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.9.0-beta2"]
                 [org.clojure/spec.alpha "0.1.134"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/core.async "0.3.443"]

                 [yada "1.2.9"]
                 [aleph "0.4.3"]
                 [manifold "0.1.7-alpha6"]
                 [com.stuartsierra/component "0.3.2"]

                 [hikari-cp "1.7.5"]
                 [org.postgresql/postgresql "42.1.4"]
                 [com.layerware/hugsql "0.4.7"]

                 [buddy "1.2.0"]
                 [one-time "0.2.0"]
                 [clj-crypto "1.0.2"
                  :exclusions [org.bouncycastle/bcprov-jdk15on bouncycastle/bcprov-jdk16]]
                 [clj-time "0.14.0"]
                 [tick "0.3.0"]

                 [org.clojure/tools.logging "0.3.1"]
                 [org.apache.logging.log4j/log4j-core "2.7"]
                 [org.apache.logging.log4j/log4j-slf4j-impl "2.7"]
                 [org.springframework.amqp/spring-rabbit "2.0.0.RELEASE"
                  :exclusions [org.springframework/spring-web org.springframework/spring-tx]]

                 [com.rabbitmq/amqp-client "5.0.0"]
                 [clojurewerkz/support     "1.1.0" :exclusions [com.google.guava/guava]]
                 [clj-http                 "3.6.1"]
                 [cheshire                 "5.7.1"]]

  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :javac-options     ["-target" "1.8" "-source" "1.8"]

  :profiles {:dev {:repl-options {:init-ns us.edwardstx.service.logging
                                  }

                   :dependencies [[binaryage/devtools "0.9.4"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  ]

                   :resource-paths ["env/dev/resources" "resources"]

                   :env {:dev true}}

             :uberjar {:env {:production true}
                       :aot :all
                       :omit-source true}}


  )
