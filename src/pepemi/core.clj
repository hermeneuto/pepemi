(ns pepemi.core
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [clojure.java.shell :as shell]
            [ring.swagger.schema :as rs]))


(s/defschema Wish
             {:name      s/Str
              :target s/Str})
;(s/defschema NewAccount (dissoc Account :id))

(defn build [target]
  (case target
    "philosobit" (do
                   (println (shell/sh "site.sh"))
                   "DONE.")
    "kabanew" (do
                (println (shell/sh "kabanew.sh"))
                "Your wish is my command.")
    "ためよ。ためため"))

(defn execute-wish [w]
  (if (= (:name w) "build")
    (do
      (println "building wish:" w)
      (build (:target w)))
    (do
      (println "failed wish:" w)
      "모야")))

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

