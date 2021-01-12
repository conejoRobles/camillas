Feature: Servicios asociados a una camilla

  Scenario: Recuperar una camilla mediante su id
    Given existe una camilla; tipo "Plegable XL", estado "Libre", year 2020
    When solicito la camilla que posee el id 1
    Then obtengo el estado Ok y la camilla con id 1 y tipo "Plegable XL"

  Scenario: Recuperar las camillas que existen en la bdd
    Given  existe una camilla; tipo "Plegable XL", estado "Libre", year 2020
    And  existe una camilla; tipo "Plegable L", estado "Ocupada", year 2018
    And  existe una camilla; tipo "Plegable M", estado "En mantencion", year 2020
    When solicito todas las camillas
    Then obtengo el estado Ok y 3 camillas
