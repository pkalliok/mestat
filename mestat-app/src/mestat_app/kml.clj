(ns mestat-app.kml
  (:require [clojure.string :refer [join]]
            [mestat-app.model :refer [point-long point-lat]]))

(def kml-mime-type "application/vnd.google-earth.kml+xml")

(defn point->kml [point]
  (let [{:keys [coord tags]} point]
    (str "<Placemark>\n"
         "<description>" (clojure.string/join ", " (map second tags))
         "</description>\n"
         "<Point><coordinates>" (point-long coord) "," (point-lat coord)
         "</coordinates></Point>\n"
         "</Placemark>\n")))

(defn pointlist->kml [pointlist]
  (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
       "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"
       "<Document>\n"
       (join "\n" (map point->kml pointlist))
       "</Document>\n"
       "</kml>\n"))

