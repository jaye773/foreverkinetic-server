(ns foreverkinetic-server.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core :refer [defresource resource request-method-in]]
            [foreverkinetic-server.views.layout :as layout]))

(defresource home
         :allowed-methods [:get :post! :put! :delete! :patch!]
         :post! (println "hi there")
         :service-available? true
         :handle-method-not-allowed "You shall not pass"
         :handle-ok "Foreverkinetic is under construction!"
         :available-media-types  ["text/plain"])

(defroutes home-routes
  (ANY "/" request home))

