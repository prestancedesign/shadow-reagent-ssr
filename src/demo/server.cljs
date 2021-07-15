(ns demo.server
  (:require ["express" :as express]
            [reagent.dom.server :as d]
            [demo.core :as core]
            ["path" :as path]))

(defn template [body]
  [:html
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name    "viewport"
            :content "width=device-width, initial-scale=1.0"}]]
   [:body
    [:div#app
     [body]]
    [:script {:src "/js/app.js"}]]])

(defn render-page [path]
  (d/render-to-static-markup [template core/app-view]))

(defn handle-request [req res]
  (.send res (render-page (.-path req))))

(defn main []
  (let [port 3000]
    (-> (express)
        (.use (.static express (.resolve path "public")))
        (.get "/" handle-request)
        (.listen port #(println "Server started on port" port)))))
