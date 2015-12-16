(ns mestat-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [mestat-app.model :as model]
            [clj-postgresql.core :as pg]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "<p>Hello World</p>\n")
  (context "/api/v1" []
    (GET "/search" [long lat]
         (model/points-near (pg/point (Float/parseFloat long) (Float/parseFloat lat)))))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

