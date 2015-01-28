(ns foreverkinetic-server.model.model
  (:require  [environ.core :refer [env]]
             [korma.db :refer :all]
             [korma.core :refer :all]
             [clojure.java.jdbc :as sql]))

#_(def db {:pool {:subprotocol "mysql"
                :subname "//127.0.0.1:3306/forever_kinetic"
                :db (env :db "forever_kinetic")
                :user (env :db-user "root")
                :password (env :db-password "1fishy4ME!")},
         :options {:naming {:keys #<core$identity clojure.core$identity@e6145dd>,
                            :fields  #<core$identity clojure.core$identity@e6145dd},
                   :delimiters ["\"" "\""],
                   :alias-delimiter " AS ",
                   :subprotocol nil}})

(def db (mysql {:subprotocol "mysql"
         :subname "//127.0.0.1:3306/forever_kinetic"
         :db (env :db "forever_kinetic")
         :user (env :db-user "root")
         :password (env :db-password "1fishy4ME!")})  )

(defdb korma-db db)

(defentity exercises)

(defn get-exercise-by-id [id]
  (select exercises
      (fields :title :description :positive_votes)
      (where {:exercise_id id})
      ))

(sql-only (select exercises))
(get-exercise-by-id 11)

(defmulti get-exercise class)

(defmethod get-exercise java.lang.Long [input] (getExerciseById input))

(defmethod get-exercise java.lang.String [input] (class input))

(defmethod get-exercise clojure.lang.PersistentArrayMap [input] (class input))

(defn get-exercise-list [limit offset])


