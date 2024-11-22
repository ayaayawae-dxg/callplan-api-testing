Feature: Get Existing Call Realization

  @smoke
  Scenario: Successfully get existing call realization
    When I send POST request to get-existing-call-realization with payload:
      """
      {
        "rayonCode": "TGRVIC0101",
        "startDate": "2024-05-06",
        "endDate": "2024-11-10"
      }
      """
    Then the response status code should be 200
    And the response should contain "success" with value "true"
    And the response should contain existing call realization data
    And each existing call realization data should match schema

  @negative
  Scenario Outline: Get Existing Call Realization with invalid date value
    When I send POST request to get-existing-call-realization with payload:
      """
        <payload>
      """
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message
    Examples:
      | payload |
      | {"rayonCode": "TGRVIC0101", "startDate": "INVALID DATE", "endDate": "2024-11-10"} |
      | {"rayonCode": "TGRVIC0101", "startDate": "2024-05-06", "endDate": "INVALID DATE"} |
      | {"rayonCode": "TGRVIC0101", "startDate": "INVALID DATE", "endDate": "INVALID DATE"} |
  
  @negative
  Scenario Outline: Get Existing Call Realization with missing required field
    When I send POST request to get-existing-call-realization with payload:
      """
        <payload>
      """
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message
    Examples:
      | payload |
      | {"startDate": "2024-05-06", "endDate": "2024-11-10"} |
      | {"rayonCode": "TGRVIC0101", "endDate": "2024-11-10"} |
      | {"rayonCode": "TGRVIC0101", "startDate": "2024-05-06"} |

