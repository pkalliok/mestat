(ns mestat-app.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [mestat-app.handler :refer :all]))

(deftest test-app
  (testing "hello route"
    (let [response (app (mock/request :get "/hello"))]
      (is (= (:status response) 200))
      (is (= (:body response) "<p>Hello World</p>\n"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

