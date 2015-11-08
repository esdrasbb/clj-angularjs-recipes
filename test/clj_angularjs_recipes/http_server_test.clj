(ns clj-angularjs-recipes.http-server-test
  (:require [clj-angularjs-recipes.http-server :refer [new-http-server]]
            [clojure.test :refer [deftest is]]
            [com.stuartsierra.component :as component]))

(def http-server (new-http-server 8080))

(deftest http-server-lifecycle
  (alter-var-root #'http-server component/start)
  (is (:server http-server) "HTTP server has been added to component")
  (is (.isStarted (:server http-server)) "HTTP server starts")
  (alter-var-root #'http-server component/stop)
  (is (= (:server http-server) nil) "HTTP server stops"))