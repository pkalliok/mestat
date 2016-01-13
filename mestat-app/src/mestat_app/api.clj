(ns mestat-app.api
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [mestat-app.kml :as kml]
            [mestat-app.model :as model]
            [mestat-app.util :refer :all]
            [clj-postgresql.core :as pg]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.format-response :refer [make-encoder]]))

(def search-handler
  (routes
    (GET "/search" [long :<< as-float lat :<< as-float limit page maxdist]
         (let [limit (as-float limit)
               page (as-float page)
               maxdist (as-float maxdist)]
           (ok (let [origin [long lat]]
                 (cons (model/make-origin origin)
                       (model/points-near origin
                                          :maxdist (or maxdist 0.3)
                                          :limit (or limit 15)
                                          :page (or page 0)))))))
    (GET "/search" [long lat]
         (status 400 {:error "missing search parameters"
                      :missing '(long lat)}))))

(def api-routes
  (wrap-restful-format
    (context "/api/v1" []
             search-handler
             (GET "/get-csrf-token" [] (ok {:csrf-token *anti-forgery-token*}))
             (GET "/test" [] (ok {:message "yes, it works"}))
             (route/not-found
               (status 404 {:error "this API call does not exist\n"})))
    :formats [:json-kw :yaml :yaml-in-html :transit-json
              (make-encoder kml/pointlist->kml kml/kml-mime-type)]))

