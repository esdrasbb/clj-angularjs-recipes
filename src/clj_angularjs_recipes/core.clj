(ns clj-angularjs-recipes.core
  (:require [com.stuartsierra.component :as component]
            [clj-angularjs-recipes.http-server :refer [server]])
  (:gen-class :main true))

(defrecord WebApp [])

(defn web-app []
  (map->WebApp {}))

(defn comp-system [config]
  (let [{:keys [port]} config]
    (component/system-map
      :server (component/using (server port) []))))


(defn -main [& args]
  (component/start
    (comp-system {:port 3000})))
