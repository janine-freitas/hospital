(ns hospital.model
  (:require [schema.core :as s])
  (:import (clojure.lang PersistentQueue)))

(def fila-vazia PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera fila-vazia
   :laboratorio1 fila-vazia
   :laboratorio2 fila-vazia
   :laboratorio3 fila-vazia})

(s/def PacienteID s/Str)
(s/def Departamento (s/queue PacienteID))
(s/def Hospital {s/Keyword Departamento})

;VALIDACOES
;(s/validate PacienteID "Janine")
;(s/validate Departamento (conj fila-vazia "Guilerme" "Janine"))
;(s/validate Hopital {:espera (conj fila-vazia "Guilerme" "Janine")})