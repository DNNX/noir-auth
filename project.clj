(defproject noir-auth "0.2.0-SNAPSHOT"
  :description "Simple authentication and authorization using lib-noir"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.6.6"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]] :plugins [[lein-midje "3.1.1"]]}})