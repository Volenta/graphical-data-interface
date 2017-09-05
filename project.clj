(defproject org.volenta/datamos.graphical-data-interface "0.1.0-SNAPSHOT"
  :description "Default Graphical data interface for dataMos"
  :url "http://theinfotect.org/datamos"
  :license {:name "GNU AFFERO GENERAL PUBLIC LICENSE, Version 3"
            :url "https://www.gnu.org/licenses/agpl-3.0.nl.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.9.0-alpha19"]
                 [org.clojure/clojurescript "1.9.908"]
                 [org.clojure/core.async  "0.3.443"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [com.taoensso/sente "1.11.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [com.taoensso/encore "2.92.0"]
                 [ring/ring-core "1.6.2"]
                 [ring/ring-defaults "0.3.1"]
                 [compojure "1.6.0"]
                 [http-kit "2.2.0"]
                 [mount "0.1.11"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.1"]
                 [day8.re-frame/async-flow-fx "0.0.8"]
                 [org.volenta/datamos "0.1.6.2"]]

  :main ^:skip-aot datamos.graphical-data-interface.base

  :source-paths ["src/clj" "src/cljs" "script"]

  :plugins [[lein-ring "0.12.1"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :ring {:handler datamos.graphical-data-interface.base/reset}

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/cljs" "script"]

                ;; The presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel {:on-jsload "datamos.graphical-data-interface.core/on-js-reload"
                           ;; :open-urls will pop open your application
                           ;; in the default browser once Figwheel has
                           ;; started and compiled your application.
                           ;; Comment this out once it no longer serves you.
                           :open-urls ["http://localhost:3449/index.html"]}

                :compiler {:main datamos.graphical-data-interface.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/graphical_data_interface.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                           ;; https://github.com/binaryage/cljs-devtools
                           :closure-defines {"re_frame.trace.trace_enabled_QMARK_" true}
                           :preloads [day8.re-frame.trace.preload
                                      devtools.preload]}}
               ;; This next build is a compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/compiled/graphical_data_interface.js"
                           :main datamos.graphical-data-interface.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this

             ;; doesn't work for you just run your own server :) (see lein-ring)

             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you are using emacsclient you can just use
             ;; :open-file-command "emacsclient"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"

             ;; to pipe all the output to the repl
             ;; :server-logfile false
             }


  ;; Setting up nREPL for Figwheel and ClojureScript dev
  ;; Please see:
  ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
  :profiles {:dev {:dependencies  [[figwheel-sidecar "0.5.13"]
                                   [com.cemerick/piggieback "0.2.2"]
                                   [binaryage/devtools "0.9.4"]
                                   [spellhouse/clairvoyant "0.0-72-g15e1e44"]
                                   [day8.re-frame/trace "0.1.6"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths  ["src/cljs" "script"]
                   ;; for CIDER
                   ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                   :repl-options  {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   ;; need to add the compliled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})