(ns core (:require
          [ring.adapter.jetty :as jetty]
          [compojure.core :refer [defroutes POST]]
          [compojure.route :as route]
          [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
          [cheshire.core :as json]
          [domain.task :as t]
          [domain.linked-list :as ll]))

(def custom-defaults
  (assoc-in site-defaults [:security :anti-forgery] false))

(defn process-handler [request]
  (let [body (-> request :body slurp json/parse-string)
        data (get body "data")]
    (cond
      (not (vector? data))
      {:status 400
       :headers {"Content-Type" "application/json"}
       :body (json/generate-string {:error "Invalid input, expected an array in 'data' key"})}

      :else
      (let [linked-list (apply t/create-result-list data)]
        {:status 200
         :headers {"Content-Type" "application/json"}
         :body (json/generate-string {:result linked-list :raw (apply ll/Cons data)})}))))


(defn select-head-handler [request] (
                                      let [body (-> request :body slurp json/parse-string)
                                           data (get body "data")]
                                      (cond (not (vector? data))
                                            {:status 400
                                             :headers {"Content-Type" "application/json"}
                                             :body (json/generate-string {:error "Invalid input, expected an array in 'data' key"})}

                                            :else
                                            (let [linked-list (apply ll/Cons data)]
                                              {:status 200
                                               :headers {"Content-Type" "application/json"}
                                               :body (json/generate-string {:result (ll/Select_Head linked-list)})}
                                              )
                                            )
                                      ))

(defn select-tail-handler [request] (
                                      let [body (-> request :body slurp json/parse-string)
                                           data (get body "data")]
                                      (cond (not (vector? data))
                                            {:status 400
                                             :headers {"Content-Type" "application/json"}
                                             :body (json/generate-string {:error "Invalid input, expected an array in 'data' key"})}

                                            :else
                                            (let [linked-list (apply ll/Cons data)]
                                              {:status 200
                                               :headers {"Content-Type" "application/json"}
                                               :body (json/generate-string {:result (ll/Select_Tail linked-list)})}
                                              )
                                            )
                                      ))


(defroutes app
  (POST "/process" request (process-handler request))
  (POST "/get-head" request (select-head-handler request))
  (POST "/get-tail" request (select-tail-handler request))
  (route/not-found "Not Found"))




(defn -main []
  (jetty/run-jetty (wrap-defaults app custom-defaults)
                   {:port 3000 :join? false}))
