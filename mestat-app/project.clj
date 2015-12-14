(defproject mestat-app "0.1.0-SNAPSHOT"
  :description "an easy tool for tagging physical places"
  :url "https://deus.solita.fi/Solita/projects/dev_pursiainen-harjoitustyot/repositories/mestat/tree/master"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler mestat-app.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
