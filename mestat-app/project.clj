(defproject mestat-app "0.1.0-SNAPSHOT"
  :description "an easy tool for tagging physical places"
  :url "https://deus.solita.fi/Solita/projects/dev_pursiainen-harjoitustyot/repositories/mestat/tree/master"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [compojure "1.4.0"]
                 [yesql "0.5.1"]
                 [ring/ring-defaults "0.1.5"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler mestat-app.handler/app
         :port 5005}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
