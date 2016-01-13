(ns mestat-app.templates
  (:require [hiccup.page :refer [html4 html5]]))

(defn main-page [latitude longitude]
  (html5
    [:head [:title "Mestat - paikkamuistikirja"]
     [:link {:rel "stylesheet" :type "text/css"
             :href "/pages/leaflet/leaflet.css"}]
     [:script {:defer "" :type "text/javascript"
               :src "/js/leaflet/leaflet.js"}]
     [:script {:defer "" :type "text/javascript"
               :src "/js/leaflet-plugins/layer/vector/KML.js"}]
     [:link {:rel "stylesheet" :type "text/css" :href "/pages/mestat.css"}]
     [:script {:async "" :type "text/javascript" :src "/js/embedmap.js"}]]
    [:body {:onload "mestat.initMestat();"}
     [:div#controls.sidebar
      [:ul#messages
       [:li#jswarning
        "You don't seem to have Javascript enabled.  There's no way I can get "
        "your current position without Javascript."]]
      [:form {:action "add-point" :method "POST"}
       [:p "Your current location:" [:br]
        [:input#latitude {:name "latitude" :type "text" :size "10"
                          :value latitude :onchange "mestat.jumpOnMap();"}]
        [:input#longitude {:name "longitude" :type "text" :size "10"
                           :value longitude :onchange "mestat.jumpOnMap();"}]
        "Tags (separated by commas):" [:br]
        [:input#tags {:name "tags" :type "text" :size "30"}]]
       [:input {:type "submit" :value "Mark location"}]]]
     [:div#map]]))

