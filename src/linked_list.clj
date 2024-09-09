(ns linked-list)

(defn- create-node [value next] {:value value :next next})

(defn- build-list [elements]
  (if (empty? elements)
    nil
    (create-node (first elements) (build-list (rest elements))))) ;

(defn Cons [head & tail]
  (create-node head (build-list tail)))

(defn Select_Head [linked-list]
  (if (nil? linked-list)
    nil
    (:value linked-list)))


(defn Select_Tail [linked-list]
  (if (nil? linked-list)
    nil
    (:next linked-list)))

(defn List_Length [linked-list]
  (if (nil? linked-list)
    0
    (inc (List_Length (Select_Tail linked-list)))))

