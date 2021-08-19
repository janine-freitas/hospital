(ns hospital.logic-test
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]))

(deftest cabe-na-fila?-test
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera []}, :espera)))

  (testing "Que nao cabe na fila quando a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]}, :espera))))

  (testing "Que nao cabe na fila quando tem mais do que cinco"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

  (testing "Que cabe na fila quando tem menos do que cinco"
    (is (cabe-na-fila? {:espera [1 2 3 4]}, :espera))
    (is (cabe-na-fila? {:espera [1 2]}, :espera)))

  (testing "Caso em que cabe na fila mas o departamento não existe"
         (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))

(deftest cehga-em-test
  (testing "aceita pessoa enquanto cabem pessoas na fila"
    (is (= {:espera [1 2 3 4 5]}
           (chega-em {:espera [1 2 3 4]}, :espera, 5)))
    (is (= {:espera [1 2 5]}
           (chega-em {:espera [1 2]}, :espera, 5))))
  (testing "nao aceita quando nao cabe na fila"
    ;CÓDIGO HORRIVEL
    ;(is (thrown? clojure.lang.ExceptionInfo
    ;             (chega-em {:espera [1 35 42 64 21]}, :espera 76)))

    (is (thrown? IllegalStateException
                (chega-em {:espera [1 35 42 64 21]}, :espera 76)))
    ))





