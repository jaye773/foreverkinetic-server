(ns foreverkinetic-server.test.model
  (:use clojure.test
        ring.mock.request
        foreverkinetic-server.model.model))

(deftest get-exercise-test
  (testing "Testing multimethod"
    (is (= (class 1) (get-exercise 1)))
    (is (= (class "string") (get-exercise "string")))
    (is (= (class {:key "val"}) (get-exercise {:key "val"})))
    )
  )

