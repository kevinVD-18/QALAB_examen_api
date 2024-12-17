
@OrdenTienda
Feature: Automatizar las apis de PetStore

  Background:
    Given la url es "https://petstore.swagger.io/v2"

  @CrearOrden
  Scenario Outline: Crear una nueva orden en la tienda
    When envío una solicitud POST con siguiente body para la orden
      | id       | petId    | quantity | shipDate           | status    | complete |
      | <id>     | <petId>  | <qty>    | <shipDate>         | <status>  | <complete> |
    Then valido el response con codigo de estado igual a 200
    And el response debe contener los mismos datos de la orden creada

    Examples:
      | id    | petId | qty | shipDate          | status | complete |
      | 13579 | 79531 | 3   | 2024-12-17T00:00Z | placed | true     |

    @ConsultarOrden
    Scenario Outline: Consultar una orden existente por su ID
      When envío una solicitud GET a "/store/order/<orderId>"
      Then valido el response con codigo de estado igual a 200
      And el response debe contener los mismos datos de la orden consultada
        | id | petId   | quantity | shipDate   | status   | complete   |
        | <orderId> | <petId> | <qty>    | <shipDate> | <status> | <complete> |

      Examples:
        | orderId | petId | qty | status | complete | shipDate          |
        | 13579   | 79531 | 3   | placed | true     | 2024-12-17T00:00Z |