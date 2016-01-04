(ns mestat-app.util
  (:require [ring.util.response :as response]))

(defn str->float [s]
  (and s (try (Float/parseFloat s) (catch NumberFormatException _ nil))))

(def ok response/response)
(defn status [status body]
  (response/status (ok body) status))

