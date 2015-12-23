(ns mestat-app.model-test
  (:require [clojure.test :refer :all]
            [clj-postgresql.core :as pg]
            [mestat-app.model :refer :all]))

(deftest test-model
  (testing "point conversions")
  (testing "points by proximity"
    (let [coord [25.46816 65.01236]
          p (points-near coord)]
      (is (= (:coord (first p)) coord))
      (is (not= (:coord (second p)) coord)))))

