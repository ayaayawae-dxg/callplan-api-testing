#Feature: Get Call Plan Recommendation
#
#  @smoke
#  Scenario: Successfully get call plan recommendation
#    When I send POST request to get-call-plan-recommendation with payload:
#      """
#      {
#        "rayonCode": "TGRVIC0101",
#        "companyId": 1,
#        "positionCode": "MR",
#        "year": 2024,
#        "month": 11,
#        "date": 25,
#        "isWeekly": true
#      }
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#    And the response should contain array of call plan recommendation data
#    And call plan recommendation data should match schema