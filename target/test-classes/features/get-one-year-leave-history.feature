#Feature: Get One Year Leave History
#
#  @smoke
#  Scenario: Successfully get one year leave history
#    When I send POST request to get-one-year-leave-history with payload:
#      """
#      {
#        "personId" : 37697,
#        "rayonCode" : "TGRVIC0101"
#      }
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#    And the response should contain array of one year leave history data
#    And each one year leave history data should match schema
#
#  @negative
#  Scenario Outline: Get one year leave history with missing required field
#    When I send POST request to get-one-year-leave-history with payload:
#      """
#        <payload>
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "false"
#    And the response should contain error message
#    Examples:
#      | payload |
#      | {"personId" : 37697}         |
#      | {"rayonCode" : "TGRVIC0101"} |
#
#  @negative
#  Scenario Outline: Get one year leave history with invalid value
#    When I send POST request to get-one-year-leave-history with payload:
#      """
#        <payload>
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#    And the response should contain empty array of one year leave history data
#    Examples:
#      | payload |
#      | {"personId" : 0, "rayonCode" : "TGRVIC0101"} |
#      | {"personId" : 37697, "rayonCode" : ""} |