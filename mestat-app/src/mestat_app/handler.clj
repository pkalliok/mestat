(ns mestat-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]

            [mestat-app.model :as model]
            [mestat-app.util :refer :all]
            [mestat-app.api :as mestat-api]
            [ring.util.response :as response]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn split-tags [tagstring]
  (map #(model/make-tag "anonymous" (clojure.string/trim %))
       (clojure.string/split tagstring (re-pattern ","))))

(def add-point-handler
  (routes
    (GET "/add-point" []
         (response/header (status 405 "Please use POST") "Allow" "POST"))
    (POST "/add-point" [latitude longitude tags]
          (let [x (str->float longitude)
                y (str->float latitude)]
            (or (and x y tags
                     (let [tags (split-tags tags)
                           coord (model/any->coord [x y])
                           point (model/make-point coord tags)]
                       (model/save-point! point)
                       (response/created
                         (str "/?latitude=" y "&longitude=" x))))
                (status 400 "Missing parameters: longitude, latitude"))))))

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
  mestat-api/api-routes
  (route/not-found "Not Found\n"))

(def app
  (wrap-defaults app-routes site-defaults))

