(ns foreverkinetic-server.test.model
  (:use clojure.test
        ring.mock.request
        foreverkinetic-server.model.model))

(deftest get-exercise-test
  (testing "Testing multimethod"
    (let [queryResult (first (get-exercise 11))]
      (is (and
            (= java.lang.Integer (class (queryResult :positive_votes)))
            (= java.math.BigInteger (class (queryResult :exercise_id)))
            (= (class "") (class (queryResult :description)))
            (= (class "") (class (queryResult :title))))))

    (let [queryResult (first (get-exercise "Supermans"))]
      (is (and
            (= java.lang.Integer (class (queryResult :positive_votes)))
            (= java.math.BigInteger (class (queryResult :exercise_id)))
            (= (class "") (class (queryResult :description)))
            (= (class "") (class (queryResult :title))))))))

