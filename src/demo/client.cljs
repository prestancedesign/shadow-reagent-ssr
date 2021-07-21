(ns demo.client
  (:require [reagent.core :as r]
            [demo.core :as core]
            [pushy.core :as pushy]
            ["react-dom" :as rd]
            [demo.core :as core]))

(defn ^:dev/after-load mount-root []
  (pushy/start! (pushy/pushy core/dispatch-route core/parse-url))
  (rd/hydrate (r/as-element [core/app-view]) (.getElementById js/document "app")))

(defn init! []
  (mount-root))
