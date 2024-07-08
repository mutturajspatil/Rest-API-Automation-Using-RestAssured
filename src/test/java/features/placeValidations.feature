Feature: Validating Place API's

Scenario Outline: Verify if place is being successfully added using AddPlace API

      Given Add Place Payload with "<name>" "<language>" "<address>"
      When user calls "AddPlaceAPI" with "POST" Http request
      Then the API call got success with status code 200
      And "status" in response body is "OK"
      And "scope" in response body is "APP"
      And verify place id created maps to "<name>" using "getPlaceAPI"

Examples:
      | name    | language | address              |
      | AAhouse | English  | World cross center   |

Scenario: Verify if Delete place functionality is working

            Given DeletePlace Paylod
            When user calls "deletePlaceAPI" with "POST" Http request
            Then the API call got success with status code 200
#          #  And "status" in response body is "OK"