(ns demo.core
  (:require [reagent.core :as r]
            [bidi.bidi :as bidi]))

(def active-route (r/atom nil))

(def routes ["/" {""     :home-page
                  "demo" :demo-page}])

(defn parse-url [url]
  (bidi/match-route routes url))

(defn dispatch-route [matched-route]
  (let [panel-name (:handler matched-route)]
    (reset! active-route panel-name)))

(def url-for (partial bidi/path-for routes))

(defn navigate-to [path]
  (dispatch-route (parse-url path)))

(defn navigation []
  [:<>
   [:a {:href (url-for :home-page)} "Home Page"]
   [:span {:style {:padding "5px"}}]
   [:a {:href (url-for :demo-page)} "Demo One"]
   [:span {:style {:padding "5px"}}]])

(defn home-page []
  (r/with-let [name (r/atom "")]
    [:<>
     [:h1 "Hello " @name]
     [:input {:type "text"
              :on-change #(reset! name (.. % -target -value))}]]))

(defn demo-page []
  [:h1 "Demo Page"])

(defn not-found-page []
  [:main.container
   [:h1 "404 - Page not found"]
   [:p "The requested page cannot be found :-("]])

(defn- panels [route]
  (case (:handler route)
    :home-page [home-page]
    :demo-page [demo-page]
    :not-found [not-found-page]))

(defn page []
  (let [active-route {:handler @active-route}]
    [panels active-route]))

(defn app-view []
  [:<>
   [navigation]
   [page]])
