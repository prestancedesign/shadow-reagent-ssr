(ns demo.server
  (:require ["express" :as express]
            [reagent.dom.server :as d]
            [demo.core :as core]))

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
  (core/navigate-to path)
  (d/render-to-string [template core/app-view]))

(defn handle-request [req res]
  (.send res (str "<!doctype html>"
                  (render-page (.-path req)))))

(defn main []
  (let [port 3000]
    (-> (express)
        (.use (.static express "public"))
        (.get "*" handle-request)
        (.listen port #(println "Server started on port" port)))))
