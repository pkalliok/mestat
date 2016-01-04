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
  (GET "/search" [long lat limit page maxdist]
       (let [x (str->float long)
             y (str->float lat)
             limit (str->float limit)
             page (str->float page)
             maxdist (str->float maxdist)]
         (or (and x y
                  (ok (let [origin [x y]]
                        (cons (model/make-origin origin)
                              (model/points-near (pg/point x y)
                                                 :maxdist (or maxdist 0.3)
                                                 :limit (or limit 15)
                                                 :page (or page 0))))))
             (status 400 {:error "missing search parameters"
                          :missing '(long lat)})))))

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

