(ns advent-of-code.day02
  (:require [advent-of-code.utils :as u]))

(defn- to-ranges
  "Converts todays puzzle input to a seq of float-pairs.
  For example, \"11-22,95-115\" will be converted to ((11 22) (95
  115))."
  [input]
  (->> input
       u/to-csvs
       u/parse-ranges))

(defn solve
  [input regexp]
  (->> input
       to-ranges
       (mapcat (fn [[start end]]
                 (filterv #(re-matches regexp (str %))
                          (range start (inc end)))))
       (reduce +)))

(defn part-1 [input] (solve input #"^(\d+)\1$"))
(defn part-2 [input] (solve input #"^(\d+)\1+$"))
