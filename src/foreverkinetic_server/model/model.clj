(ns foreverkinetic-server.model.model
  (:require  [environ.core :refer [env]]
             [korma.db :refer :all]
             [korma.core :refer :all]
             [clojure.java.jdbc :as sql]))

(def db (mysql {
         :db (env :db "forever_kinetic")
         :user (env :db-user "root")
         :password (env :db-password "1fishy4ME!")}))
(defdb korma-db db)

(defmulti get-exercise class)

(defmethod get-exercise java.lang.Long [input]
  (exec-raw ["SELECT * FROM exercises WHERE exercise_id = ?" [input]] :results))

(defmethod get-exercise java.lang.String [input]
  (exec-raw ["SELECT * FROM exercises WHERE title = ?" [input]] :results))

(defn get-exercise-list [limit offset]
  (exec-raw ["SELECT * FROM exercises LIMIT ? OFFSET ?" [limit offset]] :results))


