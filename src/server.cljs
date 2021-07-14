(ns server
  (:require ["express" :as express]
            [reagent.dom.server :as d]))

(defn template []
  [:html
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name    "viewport"
            :content "width=device-width, initial-scale=1.0"}]]
   [:body
    [:div#app
     [:h1 "Server Rendering!!!"]]]])

(defn render-page [path]
  (d/render-to-static-markup [template]))

(defn handle-request [req res]
  (.send res (render-page (.-path req))))

(defn main []
  (let [port 3000]
    (-> (express)
        (.get "/" handle-request)
        (.listen port #(println "Server started on port" port)))))
