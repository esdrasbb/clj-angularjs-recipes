(ns clj-angularjs-recipes.handler
  (:use [compojure.core]
        [ring.util.response]
        [ring.middleware.content-type]
        [ring.middleware.params])
  (:require [cheshire.core :as json]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [clj-angularjs-recipes.recipepuppy :as recipepuppy]))


(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes api-routes
           (GET "/api/recipes/:category" [category]
                (json-response {:category category :recipes (recipepuppy/get-recipes category 3)}))
           (route/resources "/")
           (route/not-found "Page not found"))

(defn wrap-dir-index
  "Middleware to force request for / to return index.html"
  [handler]
  (fn [req]
    (handler (update-in req [:uri] #(if (= "/" %) "/index.html" %)))))

(def app (-> api-routes
             wrap-params
             wrap-content-type
             wrap-dir-index
             handler/site))