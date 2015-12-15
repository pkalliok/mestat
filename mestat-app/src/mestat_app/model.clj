(ns mestat-app.model
  (:require [yesql.core :refer [defqueries]]))

(defqueries "mestat_app/queries.sql"
  {:connection "postgresql://localhost:5007/mestat"})
