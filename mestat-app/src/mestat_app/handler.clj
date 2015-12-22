(ns mestat-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [mestat-app.model :as model]
            [mestat-app.kml :as kml]
            [clj-postgresql.core :as pg]
            [ring.util.response :as response]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.format-response :refer [make-encoder]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn str->float [s]
  (and s (try (Float/parseFloat s) (catch NumberFormatException _ nil))))

(def ok response/response)
(defn status [status body]
  (response/status (ok body) status))

(def search-handler
  (GET "/search" [long lat limit page]
       (let [x (str->float long)
             y (str->float lat)
             limit (str->float limit)
             page (str->float page)]
         (or (and x y (ok (model/points-near (pg/point x y)
                                             :limit (or limit 25)
                                             :page (or page 0))))
             (status 400 {:error "missing search parameters"
                          :missing '(long lat)})))))

(def test-handler
  (GET "/test" [] (ok {:message "yes, it works"})))

(defn html-response [html]
  (response/content-type (ok html) "text/html"))

(defn serve-static [mimetype page]
  (response/content-type
    (response/resource-response page {:root "pages"}) mimetype))

(defroutes app-routes
  (GET "/" [] (serve-static "text/html" "main.html"))
  (GET "/pages/:page" [page] (serve-static "text/html" page))
  (GET "/css/:page" [page] (serve-static "text/css" page))
  (GET "/js/:page" [page] (serve-static "text/javascript" page))
  (GET "/hello" [] (html-response "<p>Hello World</p>\n"))
  (wrap-restful-format
    (context "/api/v1" []
             search-handler
             test-handler
             (route/not-found
               (status 404 {:error "this API call does not exist\n"})))
    :formats [:json-kw :yaml :yaml-in-html :transit-json
              (make-encoder kml/pointlist->kml kml/kml-mime-type)])
  (route/not-found "Not Found\n"))

(def app
  (wrap-defaults app-routes site-defaults))

