(ns foreverkinetic-server.test.handler
  (:use clojure.test
        ring.mock.request
        foreverkinetic-server.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Foreverkinetic is under construction!"))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
