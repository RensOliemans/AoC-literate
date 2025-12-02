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
  ([input] (part-1 input invalid?))
  ([input invalid-fn?]
   (->> input
        to-ranges
        (map (fn [[start end]] (range start (inc end))))
        flatten
        (filter invalid-fn?)
        (reduce +))))

(defn- any-invalid?
  [n]
  (let [ns (str n)
        c (count ns)
        ;; given an input "abc", parts will be ("a" "ab" "abc")
        parts (rest (reductions str "" ns))
        part-invalid? (fn [part]
                        (let [pc (count part)]
                          (and (= 0 (mod c pc))
                               (< pc c)
                               (= ns (apply str (repeat (/ c pc) part))))))]
    (some part-invalid? parts)))


(defn part-2
  "Day 02 Part 2"
  [input]
  (part-1 input any-invalid?))
