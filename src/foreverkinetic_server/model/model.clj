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

(declare exercises types media muscle-groups steps equipment)
(defentity exercises
           (pk :exercise_id)
           (has-one types {:fk :exercise_type_id})
           (has-many media {:fk :media_id}))
(defentity types
           (table :exercise_types)
           (pk :exercise_type_id)
           (belongs-to exercises))
(defentity media
           (table :media)
           (pk :media_id)
           (belongs-to exercises))
(defentity muscle-groups)
(defentity steps)
(defentity equipment)

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

(defn get-exercise-by-tag [tag]
  )

(sql-only (select exercises
                  (with types)
                  (fields :types)))

(select exercises
                  (with types)
                  (with media)
                  (fields :title
                          :description
                          :exercise_types.exercise_type_id
                          :positive_votes
                          :media.media_id
                          )
                  (where {:exercise_id 11}))

(defn search-exercise-title [title])

(defn search-exercise-description [description])

