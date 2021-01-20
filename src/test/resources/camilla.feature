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

  Scenario: borrar camilla base de datos
    Given existe una camilla; id 5, tipo "Plegable XL", estado "Libre", year 2020
    When elimino una camilla que posee el id 5
    Then obtengo es estado Ok y no lo encuentro con la id 5

  Scenario: editar camilla que existe en la base de datos
    Given existe una camilla; tipo "Plegable XL", estado "Libre", year 2020
    When edito la camilla que posee el id 1, cambiando el estado a "Ocupada"
    Then obtengo el estado Ok y la camilla con estado "Ocupada"

