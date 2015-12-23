(ns mestat-app.model-test
  (:require [clojure.test :refer :all]
            [clj-postgresql.core :as pg]
            [mestat-app.model :refer :all]))

(deftest test-model
  (testing "coordinate conversions"
    (let [co1 (any->coord "(15,20.73)")
          co2 (any->coord [15 20.73])]
      (is (= co1 co2))
      (is (= (coord-long co1) 15.0))
      (is (= (coord-lat co2) 20.73))))
  (testing "tag conversions"
    (let [tag1 (make-tag "foo" "bar")
          tag2 (make-tag "quux" "bar")]
      (is (= (tag-name tag1) (tag-name tag2)))
      (is (not= tag1 tag2))
      (is (= (tag-ns tag2) "quux"))))
  (testing "points by proximity"
    (let [coord [25.46816 65.01236]
          p (points-near coord)]
      (is (= (:coord (first p)) coord))
      (is (not= (:coord (second p)) coord)))))

