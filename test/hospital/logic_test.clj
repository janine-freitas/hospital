(ns hospital.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]
            [hospital.model :as h.model]
            [schema.core :as s]))
(s/set-fn-validation! true)

(deftest cabe-na-fila?-test
  (let [hospital-cheio {:espera [1 2 3 4 5]}]
    (testing "Que cabe na fila"
      (is (cabe-na-fila? {:espera []}, :espera)))

    (testing "Que nao cabe na fila quando a fila está cheia"
      (is (not (cabe-na-fila? hospital-cheio, :espera))))

    (testing "Que nao cabe na fila quando tem mais do que cinco"
      (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

    (testing "Que cabe na fila quando tem menos do que cinco"
      (is (cabe-na-fila? {:espera [1 2 3 4]}, :espera))
      (is (cabe-na-fila? {:espera [1 2]}, :espera)))

    (testing "Caso em que cabe na fila mas o departamento não existe"
      (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x))))))

(deftest chega-em-test
  (let [hospital-cheio {:espera [1 35 42 64 21]}]
    ;(testing "Aceita quando cabe na fila"
    ;  (is (= {:hospital {:espera [1 2 3 4 5]}, :resultado :sucesso}
    ;         (chega-em {:espera [1 2 3 4]}, :espera 5 )))
    ;  (is (= {:hospital {:espera [1 2 5]}, :resultado :sucesso}
    ;         (chega-em {:espera [1 2]}, :espera 5 ))))
    ;(testing "Nao aceita quando nao cabe na fila"
    ;  (is (= {:hospital hospital-ceio, :resultado :impossivel-colocar-pessoa-na-fila}
    ;         (chega-em hospital-ceio, :espera 76 ))))))
    (testing "Aceita quando cabe na fila"
      (is (= {:espera [1 2 3 4 5]}
             (chega-em {:espera [1 2 3 4]}, :espera, 5)))
      (is (= {:espera [1 2 5]}
             (chega-em {:espera [1 2]}, :espera, 5))))

    (testing "Nao aceita quando nao cabe na fila"
      (is (thrown? clojure.lang.ExceptionInfo
           (chega-em hospital-cheio, :espera 76))))))

(deftest transfere-test
  (testing "aceita pessoas se couber"
    (let [hospital-original {:espera (conj h.model/fila-vazia "5"), :raio-x h.model/fila-vazia}]
      (is (= {:espera [] :raio-x ["5"]}
             (transfere hospital-original :espera :raio-x))))
    )
    (let [hospital-original {:espera (conj h.model/fila-vazia "51" "5"), :raio-x (conj h.model/fila-vazia "13")}]
      ;(pprint (transfere hospital-original :espera :raio-x))
      (is (= {:espera ["5"] :raio-x ["13" "51"]}
             (transfere hospital-original :espera :raio-x))))
    (testing "recusa pessoas se não couber"
      (let [hospital-cheio {:espera (conj h.model/fila-vazia "5"), :raio-x (conj h.model/fila-vazia "1" "2" "53" "42" "13")}]
        (is (thrown? clojure.lang.ExceptionInfo
                    (transfere hospital-cheio :espera :raio-x)))))
    (testing "Não pode invocar transferencia sem hospital"
      (is (thrown? clojure.lang.ExceptionInfo
            (transfere nil :espera :raio-x)))))