(ns noir-auth.t-core
  (:use midje.sweet)
  (:use ring.middleware.session
        ring.middleware.session.memory)
  (:require [noir-auth.core :as core])
  (:require noir.session [noir.response :as resp]))

(defmacro in-session [body]
  `(binding [noir.session/*noir-session* (atom {})] ~body))

(facts "about authentication" 
  (in-session
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

(facts "about authorization"
  (core/authenticated (+ 2 2)) => 4
  (provided (core/current-user) => true :times 1)

  (core/authenticated (+ 2 2)) =not=> 4
  (provided
    (core/current-user) => false :times 1
    (resp/redirect "/") => anything :times 1)

  (core/authenticated (+ 2 2) ..redirect-uri..) =not=> 4
  (provided
    (core/current-user) => false :times 1
    (resp/redirect ..redirect-uri..) => anything :times 1))
