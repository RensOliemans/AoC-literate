(ns advent-of-code.day02-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day02 :refer :all]))

(deftest testinput
  (def inp "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
1698522-1698528,446443-446449,38593856-38593862,565653-565659,
824824821-824824827,2121212118-2121212124")
  (testing "Part One"
    (is (= 1227775554 (part-1 inp))))
  (testing "Part Two"
    (is (= 4174379265 (part-2 inp)))))
