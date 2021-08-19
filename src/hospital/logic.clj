(ns hospital.logic)

; existe um problema de condicional quando o departamento não existe
;(defn cabe-na-fila?
;  [hospital departamento]
;  (-> hospital
;      departamento
;      count
;      (< 5)))


; funciona para o caso de não ter o departamento
;(defn cabe-na-fila?
;  [hospital departamento]
;  (when-let [fila (get hospital departamento)]
;    (-> fila
;        count
;        (< 5))))

; também funciona mas usa o some->
; desvantagem/vantagem "menos explicito"
; desvantagem qq um que der nil, devolve nil
(defn cabe-na-fila?
  [hospital departamento]
  (some-> hospital
          departamento
          count
        (< 5)))

(defn- tenta-colocar-na-fila
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)))

;(defn chega-em
;     [hospital departamento pessoa]
;     (if-let [novo-hospital (tenta-colocar-na-fila hospital departamento pessoa)]
;       {:hospital novo-hospital, :resultado :sucesso}
;       {:hospital hospital, :resultado :impossivel-colocar-pessoa-na-fila}))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Não cabe ninguem neste departamento" {:paciente pessoa}))))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn proxima
  "Retorna o proximo da fila"
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  "Tranfere para o proximo paciente da fila_de para a fila_para"
  [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))