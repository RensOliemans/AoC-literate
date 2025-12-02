(ns advent-of-code.day02
  (:require [advent-of-code.utils :as u]))

(defn- to-ranges
  [input]
  (->> input
       u/to-csvs
       u/parse-ranges))

(defn part-1
  ([input] (part-1 input #"^(\d+)\1$"))
  ([input regexp]
   (->> input
        to-ranges
        (map (fn [[start end]] (range start (inc end))))
        flatten
        (map #(re-find regexp (str %)))
        (filter identity)
        (map first)
        (map parse-long)
        (reduce +))))


(defn part-2
  "Day 02 Part 2"
  [input]
  (part-1 input #"^(\d+)\1+$"))
