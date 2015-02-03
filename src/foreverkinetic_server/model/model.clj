(ns foreverkinetic-server.model.model
  (:require  [environ.core :refer [env]]
             [korma.db :refer :all]
             [korma.core :refer :all]
             [clojure.string :as str]
             [clojure.java.jdbc :as sql]))

(def db
  (mysql
    {:subprotocol "mysql"
     :subname (env :database-url "//localhost/forever_kinetic")
     :user (env :db-user "forever")
     :password (env :db-password "andeverandever")}))
(defdb korma-db db)

(declare exercises types media muscle-groups muscle-groups-exercise steps equipment)

(defentity exercises
           (pk :exercise_id)
           (has-one types {:fk :exercise_type_id})
           (has-many muscle-groups-exercise {:fk :exercise_id})
           (has-many steps {:fk :exercise_id})
           (has-many media {:fk :exercise_id}))
(defentity exercise-types
           (table :exercise_types))
(defentity exercise-types-exercise
           (pk :exercise_types_id)
           (table :exercise_types_exercise)
           (has-one muscle-groups {:fk :exercise_types_id}))
(defentity muscle-groups
           (table :muscle_groups))
(defentity muscle-groups-exercise
           (pk :muscle_groups_id)
           (table :muscle_groups_exercise)
           (has-one muscle-groups {:fk :muscle_groups_id}))
(defentity equipment)
(defentity equipment-exercise
           (table :equipment_exercise)
           (has-one equipment {:fk :muscle_groups_id}))
(defentity steps)
(defentity media)

(defn get-human-readable-from-id [exercise-id field column]
  (let [field-id  (keyword (str (str/replace field #"-" "_") "_id"))
        field-key (keyword field)
        field-sym (eval (symbol field))
        field-exercise-sym (eval (symbol (str field "-exercise")))]
    (->>
      (map #(% field-id) (select field-exercise-sym (where {:exercise_id exercise-id})))
      (map #(select field-sym (where {field-id %})))
      (map #((first %) column))
      (assoc {} field-key))))

(defn get-exercise-temp [exercise-id]
  (merge
    (select exercises
            (with media)
            (with steps)
            (where {:exercise_id exercise-id}))
    (map #(get-human-readable-from-id exercise-id % :name)
         ["equipment" "muscle-groups" "exercise-types"])))

(defmulti get-exercise class)

(defmethod get-exercise java.lang.Long [input]
  (select exercises
          (fields :title :description :positive_votes)
          (where {:exercise_id input})))

(defmethod get-exercise java.lang.String [exercise-id]
  (merge
    (select exercises
            (with media)
            (with steps)
            (where {:exercise_id exercise-id}))
    (map #(get-human-readable-from-id exercise-id % :name)
         ["equipment" "muscle-groups" "exercise-types"])))

(defn get-exercise-list [limit-in offset-in]
  (select exercises
          (fields :exercise_id :description :positive_votes :title)
          (limit limit-in)
          (offset offset-in)))

(get-exercise 21)

(defn get-exercise-by-tag [tag])

(sql-only (select exercise

(defn search-exercise-title [title])

(defn search-exercise-description [description])

