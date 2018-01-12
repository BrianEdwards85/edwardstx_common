(defproject us.edwardstx/edwardstx_common "1.0.5-SNAPSHOT"
  :description "Common Libs"
  :url "https://github.com/BrianEdwards85/edwardstx_common"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.9.0-RC1"]
                 [org.clojure/spec.alpha "0.1.143"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.clojure/core.async "0.3.465"]
                 [org.clojure/tools.nrepl "0.2.13"]

                 [cheshire "5.8.0"]

                 [yada "1.2.9"]
                 [aleph "0.4.4"]
                 [manifold "0.1.7-alpha6"]
                 [com.stuartsierra/component "0.3.2"]

                 [hikari-cp "1.8.2"]
                 [org.postgresql/postgresql "42.1.4"]
                 [com.layerware/hugsql "0.4.8"]

                 [buddy "2.0.0"]
                 [one-time "0.3.0"]
                 [clj-crypto "1.0.2"
                  :exclusions [org.bouncycastle/bcprov-jdk15on bouncycastle/bcprov-jdk16]]
                 [clj-time "0.14.2"]
                 [tick "0.3.0"]

                 [org.apache.logging.log4j/log4j-core "2.7"]
                 [org.apache.logging.log4j/log4j-slf4j-impl "2.7"]
                 [org.springframework.amqp/spring-rabbit "2.0.0.RELEASE"
                  :exclusions [org.springframework/spring-web org.springframework/spring-tx]]

                 [com.rabbitmq/amqp-client "5.0.0"]
                 [clojurewerkz/support     "1.1.0" :exclusions [com.google.guava/guava]]
                 [clj-http                 "3.6.1"]]

  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :javac-options     ["-target" "1.8" "-source" "1.8"]

  :profiles {:dev {:repl-options {:init-ns us.edwardstx.service.logging
                                  }

                   :dependencies [[binaryage/devtools "0.9.4"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  ]

                   :plugins [[cider/cider-nrepl "0.15.1"]]

                   :resource-paths ["env/dev/resources" "resources"]

                   :env {:dev true}}

             :uberjar {:env {:production true}
                       :aot :all
                       :omit-source true}}


  )
