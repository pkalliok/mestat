(ns mestat-app.kml
  (:require [clojure.string :refer [join]]
            [hiccup.core :refer [html]]
            [mestat-app.model :refer [tag-name coord-long coord-lat]]))

(def kml-mime-type "application/vnd.google-earth.kml+xml")

(defn point->kml-element [point]
  (let [{:keys [coord tags]} point]
    [:Placemark "\n" [:name (tag-name (first tags))]
     (if (> (count tags) 1) [:description (join ", " (map tag-name tags))]) "\n"
     [:Point [:coordinates (coord-long coord) "," (coord-lat coord)]] "\n"]))

(defn pointlist->kml-element [pointlist]
  [:kml {:xmlns "http://www.opengis.net/kml/2.2"}
   [:Document (map point->kml-element pointlist)]])

(defn pointlist->kml [pointlist]
  (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
       (html (pointlist->kml-element pointlist)) "\n"))

