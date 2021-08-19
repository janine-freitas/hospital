(ns hospital.logic)

(defn cabe-na-fila?
  [hospital departamento]
  (some-> hospital
          departamento
          count
        (< 5)))

