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
#_(defdb korma-db db)
(defentity exercises)

(defmulti get-exercise class)

(defmethod get-exercise java.lang.Long [input]
  (select exercises
          (fields :title :description :positive_votes)
          (where {:exercise_id input})))

(defmethod get-exercise java.lang.String [input]
  (select exercises
          (fields :exercise_id :description :positive_votes)
          (where {:title input})))

(defn get-exercise-list [limit-in offset-in]
  (select exercises
          (fields :exercise_id :description :positive_votes :title)
          (limit limit-in)
          (offset offset-in)))

