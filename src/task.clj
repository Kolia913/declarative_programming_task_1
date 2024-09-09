(ns task (:require [linked-list :as ll] [utils]))

(defn- calculate-length [& elements] (ll/List_Length (apply ll/Cons elements)))

(defn- count-zeros-recursion [linked-list] (if (nil? linked-list)
                                             0
                                             (let [value (ll/Select_Head linked-list)
                                                   next (ll/Select_Tail linked-list)]
                                               (+ (if (utils/is-zero-ignore-type? value) 1 0) (count-zeros-recursion next)))))

(defn- count-zeros [& elements] (count-zeros-recursion (apply ll/Cons elements)))

(defn create-result-list [& elements] (ll/Cons (apply count-zeros elements) (apply calculate-length elements)))