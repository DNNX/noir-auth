(ns noir-auth.t-core
  (:use midje.sweet)
  (:use ring.middleware.session
        ring.middleware.session.memory)
  (:require [noir-auth.core :as core])
  (:require noir.session))

(facts "about authentication" 
  (binding [noir.session/*noir-session* (atom {})]
    (let [generated-password (core/generate-password "PS@RD")
          users [{:user "UNAME" :password generated-password}]]
      (core/current-user) => falsey
      (core/logout) => anything
      
      (core/authenticate "UNAME" "PS@RD" users) => truthy
      (core/current-user) => (contains {:user "UNAME"})
      
      (core/logout) => anything
      (core/current-user) => falsey
      
      (core/authenticate "UNAME" "wrong-password" users) => falsey
      (core/current-user) => falsey

      (core/authenticate "wrong" generated-password users) => falsey
      (core/current-user) => falsey)))
