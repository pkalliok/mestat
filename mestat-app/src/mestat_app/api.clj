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

(defroutes search-handler
  (GET "/search" [long :<< as-float lat :<< as-float limit page maxdist]
       (let [limit (as-float limit)
             page (as-float page)
             maxdist (as-float maxdist)]
         (ok (let [origin (model/make-coord long lat)]
               (cons (model/make-origin origin)
                     (model/points-near origin
                                        :maxdist (or maxdist 0.3)
                                        :limit (or limit 15)
                                        :page (or page 0)))))))
  (GET "/search" [long lat]
       (status 400 {:error "missing search parameters"
                    :missing '(long lat)})))

(defroutes point-query-handler
  (GET "/point/:long/:lat" [long :<< as-float lat :<< as-float]
       (let [result (model/point-at (model/make-coord long lat))]
         (if result (ok result)
           (status 404 {:error "no such point" :long long :lat lat}))))
  (GET "/point/:long/:lat" [long lat]
       (status 400 {:error "malformed parameters"
                    :types {:long :float :lat :float}}))
  (GET "/point" []
       (status 400 {:error "missing parameters"
                    :missing '(long lat)})))

(defroutes point-add-handler
  (PUT "/point/:long/:lat" [long :<< as-float lat :<< as-float
                            :as {point :body-params}]
       (or (and (model/point? point)
                (= (:coord point) (model/make-point long lat))
                (do (model/save-point! point)
                    (status 201 {:message "created"})))
           (status 400 {:error "invalid point structure"
                        :actual point
                        :shouldbe (model/make-point
                                    (model/make-coord long lat)
                                    [(model/make-tag "testuser" "example")])})))
  (PUT "/point/:long/:lat" [long lat]
       (status 400 {:error "malformed parameters"
                    :types {:long :float :lat :float}}))
  (PUT "/point" []
       (status 400 {:error "missing parameters"
                    :missing '(long lat)})))

(def api-routes
  (wrap-restful-format
    (context "/api/v1" []
             search-handler
             point-query-handler
             point-add-handler
             ;point-import-handler
             (GET "/get-csrf-token" [] (ok {:csrf-token *anti-forgery-token*}))
             (GET "/test" [] (ok {:message "yes, it works"}))
             (route/not-found
               (status 404 {:error "this API call does not exist\n"})))
    :formats [:json-kw :yaml :yaml-in-html :transit-json
              (make-encoder kml/pointlist->kml kml/kml-mime-type)]))

