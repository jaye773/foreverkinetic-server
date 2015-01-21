(ns foreverkinetic-server.model.model
  (:require  [environ.core :refer [env]]
             [clojure.java.jdbc :as sql]))

(def db {:subprotocol "mysql"
         :subname (env :database-url "//127.0.0.1:3306/forever_kinetic")
         :user (env :db-user "forever")
         :password (env :db-password "andeverandever") })


