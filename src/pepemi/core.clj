(ns pepemi.core
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [clojure.tools.logging :as log]
            [clojure.java.shell :as shell]
            [ring.swagger.schema :as rs]))


(s/defschema Wish
             {:name   s/Str
              :target s/Str})
;(s/defschema NewAccount (dissoc Account :id))

(defn build [target]
  (case target
    "philosobit"
                (try
                  (log/info "START: site.sh")
                  (shell/sh "site.sh")
                  (log/info "DONE: site.sh")
                  "DONE"
                  (catch Exception e
                    (log/error "ERROR: site.sh", e)
                    "Code:101"
                    ))
    "kabanew"
                (try
                  (log/info "START: kabanew.sh")
                  (shell/sh "kabanew.sh")
                  (log/info "DONE: kabanew.sh")
                  "Your wish is my command."
                  (catch Exception e
                    (log/error "ERROR: kananew.sh", e)
                    "Code:102"
                     ))

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
               (log/info "POST[wish]" wish)
               (ok (execute-wish wish)))
             (GET "/wish" {headers :headers params :params}
               :summary "List of wishes"
                   (log/info "GET[wish]" (str headers) (str params))
                   (ok {:name "build" :target "?"})))))

(println "Initializing ")
