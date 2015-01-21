(defproject foreverkinetic-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [korma "0.3.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [ring-server "0.3.1"]]
  :plugins [[lein-ring "0.8.12"]]
  :min-lein-version "2.5.0"
  :ring {:handler foreverkinetic-server.handler/app
         :init foreverkinetic-server.handler/init
         :destroy foreverkinetic-server.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
