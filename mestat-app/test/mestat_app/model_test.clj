(ns mestat-app.model-test
  (:require [clojure.test :refer :all]
            [clj-postgresql.core :as pg]
            [mestat-app.model :refer :all]))

(deftest test-model
  (testing "points by proximity"
    (let [loc "(25.46816,65.01236)"
          p (points-near loc)]
      (is (= (:coord (first p)) loc))
      (is (not= (:coord (second p)) loc)))))

