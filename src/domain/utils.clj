(ns domain.utils)

(defn is-zero-ignore-type? [x] (cond
                                 (string? x) (= x "0")
                                 (number? x) (zero? x)
                                 :else false))


;; (defn to-vector [input]
;;   (try
;;     (->> (string/split input #",")
;;          (map string/trim)
;;          (map (fn [x]
;;                 (try
;;                   (let [num (read-string x)]
;;                     (if (number? num)
;;                       num
;;                       x))
;;                   (catch Exception e
;;                     x))))
;;          (vec))
;;     (catch Exception e
;;       (println "Error processing input:" (.getMessage e))
;;       [])))      