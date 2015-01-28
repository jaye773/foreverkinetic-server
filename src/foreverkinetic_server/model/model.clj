(ns foreverkinetic-server.model.model
  (:require  [environ.core :refer [env]]
             [korma.db :refer :all]
             [korma.core :refer :all]
             [clojure.java.jdbc :as sql]))

(def db
  (mysql
    {:subprotocol "mysql"
     :subname (env :database-url "//localhost/forever_kinetic")
     :user (env :db-user "forever")
     :password (env :db-password "andeverandever")}))
(defdb korma-db db)

(sql/query db ["SELECT * FROM exercises WHERE exercise_id = ?" "11"])

(defmulti get-exercise class)

(defmethod get-exercise java.lang.Long [input]
  (exec-raw ["SELECT * FROM exercises WHERE exercise_id = ?" [input]] :results))

(defmethod get-exercise java.lang.String [input]
  (exec-raw ["SELECT * FROM exercises WHERE title = ?" [input]] :results))

(defn get-exercise-list [limit offset]
  (exec-raw ["SELECT * FROM exercises LIMIT ? OFFSET ?" [limit offset]] :results))


