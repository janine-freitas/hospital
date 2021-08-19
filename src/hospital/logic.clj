(ns hospital.logic)

(defn cabe-na-fila?
  [hospital departamento]
  ;(count (get hospital departamento))
  (-> hospital
      departamento
      count
      (< 5)))
