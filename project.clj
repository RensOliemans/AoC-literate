(defproject advent-of-code "0.1.0"
  :description "Advent of Code Literate Solutions"
  :url "https://codeberg.org/RensOliemans/AoC-literate"
  :license {:name "GPL-3.0-only"
            :url "https://opensource.org/license/gpl-3-0"}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.clojure/tools.cli "1.0.219"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [org.clojure/core.match "1.0.1"]
                 [org.clojure/data.priority-map "1.1.0"]]
  :plugins [[lein-kibit "0.1.6"]]
  :repl-options {:init-ns advent-of-code.core})
