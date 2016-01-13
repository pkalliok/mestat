(ns mestat-app.util
  (:require [ring.util.response :as response]))

(defn as-float [s]
  (and s (try (Double/parseDouble s) (catch NumberFormatException _ nil))))

(def ok response/response)
(defn status [status body]
  (response/status (ok body) status))

