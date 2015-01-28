(ns foreverkinetic-server.test.model
  (:use clojure.test
        ring.mock.request
        foreverkinetic-server.model.model))

(deftest get-exercise-test
  (testing "Testing multimethod"
    (let [queryResult (first (get-exercise 11))]
      (is (= java.lang.Integer (class (queryResult :positive_votes))))
      (is (not= java.math.BigInteger (class (queryResult :exercise_id))))
      (is (= (class "") (class (queryResult :description))))
      (is (= (class "") (class (queryResult :title)))))

    (let [queryResult (first (get-exercise "Supermans"))]
      (is (= java.lang.Integer (class (queryResult :positive_votes))))
      (is (= java.math.BigInteger (class (queryResult :exercise_id))))
      (is (= (class "") (class (queryResult :description))))
      (is (not= (class "") (class (queryResult :title)))))))

(deftest get-exercise-list-test
  (testing "Testing limits"
    (is (= 2 (count (get-exercise-list 2 2))))
    (is (= 20 (count (get-exercise-list 20 2))))
    (is (= 10 (count (get-exercise-list 10 2)))))

  (testing "Testing offset"
    (is (= 111 ((last (get-exercise-list 10 2)) :exercise_id)))
    (is (= 331 ((last (get-exercise-list 10 24)) :exercise_id)))
    (is (= 91 ((last (get-exercise-list 10 0)) :exercise_id)))))

(run-tests)
