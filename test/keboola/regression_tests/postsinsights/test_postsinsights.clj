(ns keboola.regression-tests.postsinsights.test-postsinsights
  (:require [clj-http.fake :refer :all]
            [clojure.test :as t :refer :all]
            [keboola.facebook.extractor.output :refer [reset-columns-map]]
            [keboola.facebook.insights-extractor.core :refer [prepare-and-run]]
            [keboola.facebook.insights-extractor.sync-actions
             :refer
             [disable-log-token]]
            [keboola.regression-tests.outdirs-check :as outdirs-check]
            [keboola.regression-tests.postsinsights.apicalls :as apicalls]
            [keboola.test-utils.core :as test-utils]))

(deftest postsinsights-test
  (let [tmp-dir (.getPath (test-utils/mk-tmp-dir! "postsinsights"))]
    (disable-log-token)
    (println "testing dir:" tmp-dir)
    (println "expected dir:" "test/keboola/regression_tests/postsinsights")
    (test-utils/copy-config-tmp "test/keboola/regression_tests/postsinsights" tmp-dir)
    (with-global-fake-routes-in-isolation
      apicalls/recorded
      (reset-columns-map)
      (prepare-and-run tmp-dir)
      (outdirs-check/is-equal "test/keboola/regression_tests/postsinsights" tmp-dir)
      )))
