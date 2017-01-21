(ns pepemi.core
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [clojure.java.shell :only [sh]]
            [ring.swagger.schema :as rs]))


(s/defschema Wish
             {:name      s/Str
              :target s/Str})

;(s/defschema NewAccount (dissoc Account :id))

(defn execute-wish[w]
  (if (and (= (:target w) "philosobit")
           (= (:name w) "build"))
    (do
      (println "executing wish:" w)
      (clojure.java.shell "site.sh")
      "Your wish is my command.")
    (do
      (println "failed wish:" w)
      "ためよ。ためため"
      )
    ))


(def app
  (api
    {:swagger
     {:ui   "/api-doc"
      :spec "/api-doc/swagger"
      :data {:info {:title "Service"}
             :tags [{:name "api"}]}}}
    (context "/api" []
             :tags ["api"]
             (POST "/wish" []
                   :body [wish (describe Wish "New Wish")]
                   :summary "Create wish."
                   (println wish)
                   (ok (execute-wish wish)))
             (GET "/wish" []
                   :summary "List of wishes"
                   (ok {:name "build" :target "?"})))))

