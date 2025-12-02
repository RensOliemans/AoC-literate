(ns advent-of-code.day02
  (:require [advent-of-code.utils :as u]))

(defn- to-ranges
  [input]
  (->> input
       u/to-csvs
       u/parse-ranges))

(defn- invalid?
  "Returns true if n is invalid (sequence of digits repeated twice)"
  [n]
  (let [ns (str n)
        halfway (/ (count ns) 2)]
    (and (even? (count ns))
         (= (subs ns 0 halfway)
            (subs ns halfway)))))

(defn part-1
  "Day 02 Part 1"
  [input]
  (->> input
       to-ranges
       (map (fn [[start end]] (range start end)))
       flatten
       (filter invalid?)
       (reduce +)))


(defn part-2
  "Day 02 Part 2"
  [input]
  "Implement this part")
