(ns clj-angularjs-recipes.http-server
  (:require [compojure.core :as compojure]
            [com.stuartsierra.component :as component]
            [clj-angularjs-recipes.handler :as handler]
            [ring.adapter.jetty :as jetty]))

(compojure/defroutes app
                     handler/app)

(defrecord Server [port server]
  component/Lifecycle
  (start [component]
    (println ";; Starting HTTP server")
    (let [server (jetty/run-jetty app {:port port :join? false})]
      (assoc component :server server)))
  (stop [component]
    (println ";; Stopping HTTP server")
    (.stop server)
    (assoc component :server nil)))

(defn server
  [port]
  (map->Server {:port port}))
