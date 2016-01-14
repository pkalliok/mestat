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

(def make-coord vector)
(def coord-long first)
(def coord-lat second)

(defn coord? [c] (and (vector? c) (= 2 (count c))
                      (number? (first c)) (number? (second c))))

(def make-tag vector)
(def tag-ns first)
(def tag-name second)

(defn tag? [t] (and (vector? t) (= 2 (count t))
                    (string? (first t)) (string? (second t))))

(defn make-point [coord tags & {:as params}]
  (assoc params :coord coord :tags tags))

(defn point? [p]
  (and (map? p) (:coord p) (coord? (:coord p))
       (:tags p) (coll? (:tags p)) (every? tag? (:tags p))))

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
    (db-points-near {:coord (pg/point p) :page page :limit limit
                     :username username :tagpat tagpat :mindate mindate
                     :maxdate maxdate :maxdist maxdist})))

(defn point-at [p]
  (first (point-query-to-pointlist
           (db-points-near {:coord (pg/point p) :page 0 :limit 1
                            :username nil :tagpat nil :mindate nil
                            :maxdate nil :maxdist 0.0000001}))))

(defn id-for-coord-maybe-create [coord]
  (let [params {:coord (pg/point coord)}]
    (when (empty? (db-location-id params)) (db-insert-location! params))
    (:id (first (db-location-id params)))))

(defn id-for-tag-maybe-create [tag]
  (let [params {:ns (tag-ns tag) :name (tag-name tag)}]
    (when (empty? (db-tag-id params)) (db-insert-tag! params))
    (:id (first (db-tag-id params)))))

(defn ensure-tag-bound! [tag-id location-id]
  (let [params {:tag tag-id :loc location-id}]
    (when (empty? (db-tag-bound? params)) (db-bind-tag! params))))

(defn save-point! [point]
  (let [loc-id (id-for-coord-maybe-create (:coord point))]
    (doseq [tag (:tags point)]
      (ensure-tag-bound! (id-for-tag-maybe-create tag) loc-id))))

