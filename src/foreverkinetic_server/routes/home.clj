(ns foreverkinetic-server.routes.home
  (:require [compojure.core :refer :all]
            [foreverkinetic-server.views.layout :as layout]))

(defn home []
	"Foreverkinetic is under construction!")

(defroutes home-routes
  (GET "/" [] (home)))
