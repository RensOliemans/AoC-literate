(ns advent-of-code.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day01 :refer :all]))

(deftest testinput
  (def inp "L68
L30
R48
L5
R60
L55
L1
L99
R14
L82
")
  (testing "Part One"
    (is (= 3 (part-1 inp))))
  (testing "Part Two"
    (is (= 6 (part-2 inp)))))

(deftest convert-rotation-test
  (testing "L"
    (is (= -68 (convert-rotation "L68")))
    (is (= -1 (convert-rotation "L1"))))
  (testing "R"
    (is (= 34 (convert-rotation "R34")))
    (is (= 156 (convert-rotation "R156")))))

(deftest zero-counters-test
  (testing "Positive numbers, no zeros"
    (is (= 0 (zero-counters 55 45)))
    (is (= 0 (zero-counters 99 1))))
  (testing "Negative numbers, no zeros"
    (is (= 0 (zero-counters -55 -45)))
    (is (= 0 (zero-counters -5900 -5840)))
    (is (= 0 (zero-counters -9900 -9925))))
  (testing "Ends at zero"
    (is (= 1 (zero-counters -1 0)))
    (is (= 1 (zero-counters 155 100)))
    (is (= 1 (zero-counters -155 -100))))
  (testing "Higher numbers"
    (is (= 1 (zero-counters -100 -200)))
    (is (= 2 (zero-counters 105 305)))
    (is (= 1 (zero-counters -6299 -6300)))
    (is (= 3 (zero-counters -101 101)))))
