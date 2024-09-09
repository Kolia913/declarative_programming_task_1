(ns utils)

(defn is-zero-ignore-type? [x] (cond
                                 (string? x) (= x "0")
                                 (number? x) (zero? x)
                                 :else false))