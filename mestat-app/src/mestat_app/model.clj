(ns mestat-app.model
  (:require [yesql.core :refer [defqueries]]
            [clj-postgresql.core :as pg]))

(defqueries "mestat_app/queries.sql" {:connection (pg/spec)})

(defn str->point [s]
  (let [p (pg/point s)]
    [(.x p) (.y p)]))

(def point-long first)
(def point-lat second)

(defn point-query-to-pointlist [pquery]
  (map (fn [point]
         {:coord (str->point (:coord (first point)))
          :tags (map #(map % [:ns :name]) point)})
       (partition-by :id pquery)))

(defn points-near [p & {:keys [page limit] :or {page 0, limit 25}}]
  (point-query-to-pointlist
    (db-points-near {:point p, :page page, :limit limit})))

