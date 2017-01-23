(defproject pepemi "0.1.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [metosin/compojure-api "1.0.2"]]
  :ring {:handler pepemi.core/app}
  :profiles {:dev
               {:plugins      [[lein-ring "0.9.7"]]
                :dependencies [[javax.servlet/servlet-api "2.5"]]}})
