(ns incrudible.handler.example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]
            [integrant.core :as ig]))

(defmethod ig/init-key :incrudible.handler/example [_ options]
  (context "/example" []
    (GET "/" []
      (io/resource "incrudible/handler/example/example.html"))))
