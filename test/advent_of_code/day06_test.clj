(ns advent-of-code.day06-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day06 :refer :all]))

(deftest testinput
  (def inp "")
  (testing "Part One"
    (is (= "Implement this part" (part-1 inp))))
  (testing "Part Two"
    (is (= "Implement this part" (part-2 inp)))))
