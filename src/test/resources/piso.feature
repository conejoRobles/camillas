Feature: Servicios asociados a un piso

  Scenario: Recuperar una piso mediante su id
    Given existe un piso; nombre "Piso 1", estado "Habilitado", numero de habitaciones 15
    When solicito el piso que posee el id 8
    Then obtengo el estado Ok y el piso con id 8 y nombre "Piso 1"

  Scenario: Recuperar pisos que existen en la bdd
    Given  existe un piso; nombre "Piso 1", estado "Habilitado", numero de habitaciones 20
    And  existe un piso; nombre "Piso 2", estado "Habilitado", numero de habitaciones 10
    And  existe un piso; nombre "Piso 3", estado "Habilitado", numero de habitaciones 15
    When solicito todos los pisos
    Then obtengo el estado Ok y 3 pisos

  Scenario: borrar piso base de datos
    Given existe un piso; id 12, nombre "Piso 123", estado "Habilitado", numero de habitaciones 20
    When elimino un piso que posee el id 12
    Then obtengo es estado Ok y no lo encuentro el piso con la id 12

  Scenario: editar piso que existe en la base de datos
    Given existe un piso; nombre "Piso 1", estado "Habilitado", numero de habitaciones 20
    When edito el piso que posee el id 1, cambiando el estado a "No Habilitado"
    Then obtengo el estado Ok y el piso con estado "No Habilitado"

  Scenario: Agregar un NUEVO camilla al hospital
    Given existe un piso; nombre "Piso 10", estado "Habilitado", numero de habitaciones 20
    When solicito se agregue un piso al hospital
    Then obtengo el estado "Created" y el piso agregado tiene como nombre "Piso 10" y numero de habitaciones 20
