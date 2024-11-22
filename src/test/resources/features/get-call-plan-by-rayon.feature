#Feature: Get Call Plan By Rayon
#
#  @smoke
#  Scenario Outline: Successfully get call plan by rayon
#    When I send POST request to get-call-plan-by-rayon with payload:
#       """
#        <payload>
#       """
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#    And the response should contain array of call plan by rayon data
#    And call plan by rayon data should match schema "<schema_file>"
#    Examples:
#      | payload                                                                                                         | schema_file                     |
#      | {"employeeId": "10200015052","rayonCode": "TGRVIC0101","period": "2024-03-21","companyId": 1,"isWeekly": false} | GetCallPlanByRayon-Monthly.json |
#      | {"employeeId": "10200015052","rayonCode": "TGRVIC0101","period": "2024-03-21","companyId": 1,"isWeekly": true}  | GetCallPlanByRayon-Weekly.json  |
#
#  @negative
#  Scenario Outline: Get call plan by rayon with invalid period
#    When I send POST request to get-call-plan-by-rayon with payload:
#      """
#      {
#        "employeeId": "10200015052",
#        "rayonCode": "TGRVIC0101",
#        "companyId": 1,
#        "period": "<period>",
#        "isWeekly": <isWeekly>
#      }
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "false"
#    And the response should contain error message
#    Examples:
#      | period         | isWeekly |
#      | INVALID_PERIOD | false    |
#      | INVALID_PERIOD | true     |
