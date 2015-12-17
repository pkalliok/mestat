(ns mestat-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [mestat-app.model :as model]
            [clj-postgresql.core :as pg]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def search-handler
  (GET "/search" [long lat limit page]
       (and long lat
            (try (model/points-near (pg/point (Float/parseFloat long)
                                              (Float/parseFloat lat))
                                    :limit (or limit 25)
                                    :page (or page 0))
                 (catch NumberFormatException _ nil)))))

(defroutes app-routes
  (GET "/" [] "<p>Hello World</p>\n")
  (wrap-restful-format
    (context "/api/v1" []
             search-handler
             (route/not-found {:status "error", :message "not found",
                               :description "this API call does not exist"})))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

