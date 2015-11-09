(ns clj-angularjs-recipes.http-server-test
  (:require [clj-angularjs-recipes.http-server :refer [server]]
            [clojure.test :refer [deftest is]]
            [com.stuartsierra.component :as component]))

(def http-server (server 8080))

(deftest http-server-lifecycle
  (alter-var-root #'http-server component/start)
  (is (:server http-server) "HTTP server has been added to component")
  (is (.isStarted (:server http-server)) "HTTP server starts")
  (alter-var-root #'http-server component/stop)
  (is (nil? (:server http-server)) "HTTP server stops"))