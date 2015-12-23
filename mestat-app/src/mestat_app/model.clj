(ns mestat-app.model
  "This namespace has handlers for the internal data structures of mestat:
  coordinates (on earth) are represented with a two-place vector [long, lat];
  points are coordinates with attached metadata and are represented by a
  map with keys :coord (obligatory), :tags (optional) and :modified (optional);
  pointlists are just lists of points in order of proximity to some coord;
  tags are two-place vectors with namespace and tag name."
  (:require [yesql.core :refer [defqueries]]
            [clj-postgresql.core :as pg]))

(defqueries "mestat_app/queries.sql" {:connection (pg/spec)})

; pg/point seems to be able to construct points from both strings and vectors

(defn any->coord [s]
  (let [p (pg/point s)]
    [(.x p) (.y p)]))

(def coord-long first)
(def coord-lat second)

(def make-tag vector)
(def tag-ns first)
(def tag-name second)

(defn make-point [coord tags & {:as params}]
  (assoc params :coord coord :tags tags))

(defn make-origin [coord]
  (make-point coord (list (make-tag "system" "origin"))))

(defn point-query-to-pointlist [pquery]
  (map (fn [point]
         {:coord (any->coord (:coord (first point)))
          :tags (map #(mapv % [:ns :name]) point)})
       (partition-by :id pquery)))

(defn points-near [p & {:keys [page limit username tagpat mindate maxdate
                               maxdist]
                        :or {page 0, limit 100}}]
  (point-query-to-pointlist
    (db-points-near {:point (pg/point p) :page page :limit limit
                     :username username :tagpat tagpat :mindate mindate
                     :maxdate maxdate :maxdist maxdist})))

