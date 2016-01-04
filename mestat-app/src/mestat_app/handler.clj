(ns mestat-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [mestat-app.model :as model]
            [mestat-app.kml :as kml]
            [clj-postgresql.core :as pg]
            [ring.util.response :as response]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.format-response :refer [make-encoder]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn str->float [s]
  (and s (try (Float/parseFloat s) (catch NumberFormatException _ nil))))

(def ok response/response)
(defn status [status body]
  (response/status (ok body) status))

(defn split-tags [tagstring] ["dummy"])

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

(def add-point-handler
  (routes
    (GET "/add-point" []
         (response/header (status 405 "Please use POST") "Allow" "POST"))
    (POST "/add-point" [latitude longitude tags]
          (let [x (str->float longitude)
                y (str->float latitude)
                tags (split-tags tags)
                coord (model/any->coord [x y])
                point (model/make-point coord tags)]
            (or (and x y tags
                     (do (model/save-point! point)
                         (response/created
                           (str "/?longitude=" x "&latitude=" y))))
                (status 400 "Missing parameters: longitude, latitude"))))))

(def test-handler
  (GET "/test" [] (ok {:message "yes, it works"})))

(defn html-response [html]
  (response/content-type (ok html) "text/html"))

(defn serve-static [mimetype page]
  (response/content-type
    (response/resource-response page {:root "pages"}) mimetype))

(defroutes app-routes
  (GET "/" [] (serve-static "text/html" "main.html"))
  add-point-handler
  (route/resources "/pages" {:root "pages"})
  (route/resources "/js" {:root "pages"} {"js" "text/javascript"})
  (GET "/hello" [] (html-response "<p>Hello World</p>\n"))
  (wrap-restful-format
    (context "/api/v1" []
             search-handler
             (GET "/get-csrf-token" [] (ok {:csrf-token *anti-forgery-token*}))
             test-handler
             (route/not-found
               (status 404 {:error "this API call does not exist\n"})))
    :formats [:json-kw :yaml :yaml-in-html :transit-json
              (make-encoder kml/pointlist->kml kml/kml-mime-type)])
  (route/not-found "Not Found\n"))

(def app
  (wrap-defaults app-routes site-defaults))

