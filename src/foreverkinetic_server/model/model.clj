(ns foreverkinetic-server.model.model
  (:require  [environ.core :refer [env]]
             [datomic.api :as d]))

(def uri "datomic:mem://hello")

(d/create-database  uri)

(def conn (d/connect uri))

(def datom ["db/add", (d/tempid "db.part/user"), "db/doc", "hello world"])

(def resp (d/transact conn [datom]))

(def database (d/db conn))

(d/q '[:find ?e . :where [?e :db/doc "hello world"]] database)

