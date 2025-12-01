(ns advent-of-code.day01
  (:require [advent-of-code.utils :as u]))

(defn- convert-rotation "Converts a rotation string into a number.
  For example, L68 would be -68, and R48 would be 48."
  [rot]
  (let [direction (first rot)
        number (subs rot 1)
        number (Integer/parseInt number)]
    (condp = direction
      \L (- number)
      \R number)))

(defn part-1
  "Day 01 Part 1"
  [input]
  (->> input
       u/to-lines
       (map convert-rotation)
       (reductions + 50)
       (map #(mod % 100))
       (filter zero?)
       count))

(defn- zero-counters
  "When moving the dial from `from` to `to`, how often does the dial
  point at `0`, during or after the rotation?"
  [from to]
  (let [divver (if (< from to) Math/floorDiv Math/ceilDiv)]
    (let [a (divver from 100)
          b (divver to   100)]
      (Math/abs (- b a)))))

(defn part-2
  "Day 01 Part 2"
  [input]
  (->> input
       u/to-lines
       (map convert-rotation)
       (reductions + 50)
       (partition 2 1)
       (map (fn [[x y]] (zero-counters x y)))
       (reduce +)))
