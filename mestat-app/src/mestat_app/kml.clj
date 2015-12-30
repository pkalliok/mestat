(ns mestat-app.kml
  (:require [clojure.string :refer [join]]
            [mestat-app.model :refer [tag-name coord-long coord-lat]]))

(def kml-mime-type "application/vnd.google-earth.kml+xml")

(defn point->kml [point]
  (let [{:keys [coord tags]} point]
    (str "<Placemark>\n"
         "<name>" (tag-name (first tags)) "</name>\n"
         "<description>" (join ", " (map tag-name tags)) "</description>\n"
         "<Point><coordinates>" (coord-long coord) "," (coord-lat coord)
         "</coordinates></Point>\n"
         "</Placemark>\n")))

(defn pointlist->kml [pointlist]
  (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
       "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"
       "<Document>\n"
       (join "\n" (map point->kml pointlist))
       "</Document>\n"
       "</kml>\n"))

