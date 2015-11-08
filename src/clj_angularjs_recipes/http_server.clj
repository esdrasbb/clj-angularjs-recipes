(ns clj-angularjs-recipes.http-server
  (:require [compojure.core :as compojure]
            [com.stuartsierra.component :as component]
            [clj-angularjs-recipes.core :as core]
            [ring.adapter.jetty :as jetty]))

(compojure/defroutes app
                     core/app)

(defrecord HTTPServer [port server]
  component/Lifecycle
  (start [component]
    (println ";; Starting HTTP server")
    (let [server (jetty/run-jetty app {:port port :join? false})]
      (assoc component :server server)))
  (stop [component]
    (println ";; Stopping HTTP server")
    (.stop server)
    (assoc component :server nil)))

(defn new-http-server
  [port]
  (map->HTTPServer {:port port}))
