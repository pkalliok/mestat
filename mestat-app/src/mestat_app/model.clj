(ns mestat-app.model
  (:require [yesql.core :refer [defqueries]]))

(defqueries "mestat_app/queries.sql"
  {:connection "postgresql://localhost:5007/mestat"})

(defn point-query-to-pointlist [pquery]
  (map (fn [point]
         {:coord (:coord (first point))
          :tags (map #(map % [:ns :name]) point)})
       (partition-by :id pquery)))

(defn points-near [p & {:keys [page limit] :or {page 0, limit 25}}]
  (point-query-to-pointlist
    (db-points-near {:point p, :page page, :limit limit})))
