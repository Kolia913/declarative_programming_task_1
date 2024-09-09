(ns core (:require
          [ring.adapter.jetty :as jetty]
          [compojure.core :refer [defroutes POST]]
          [compojure.route :as route]
          [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
          [cheshire.core :as json]
          [domain.task :as t]))

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
         :body (json/generate-string linked-list)}))))


(defroutes app
  (POST "/process" request (process-handler request))
  (route/not-found "Not Found"))


(defn -main []
  (jetty/run-jetty (wrap-defaults app custom-defaults)
                   {:port 3000 :join? false}))