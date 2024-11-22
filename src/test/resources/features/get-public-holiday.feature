Feature: Get Public Holiday

  @smoke
  Scenario: Successfully get public holiday
    When I send POST request to get-public-holiday with payload:
      """
      {
        "startDate": "2024-01-01",
        "endDate": "2024-02-25"
      }
      """
    Then the response status code should be 200
    And the response should contain "success" with value "true"
    And the response should contain array of public holiday data
    And each public holiday data should match schema
  
  @negative
  Scenario Outline: Get Public Holiday with invalid date value
    When I send POST request to get-public-holiday with payload:
      """
        <payload>
      """
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message
    Examples:
      | payload |
      | {"startDate": "INVALID DATE", "endDate": "2024-02-25"} |
      | {"startDate": "2024-01-01", "endDate": "INVALID DATE"} |
      | {"startDate": "INVALID DATE", "endDate": "INVALID DATE"} |

  @negative
  Scenario Outline: Get Public Holiday with missing required field
    When I send POST request to get-public-holiday with payload:
      """
        <payload>
      """
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message
    Examples:
      | payload |
      | {"endDate": "2024-02-25"} |
      | {"startDate": "2024-01-01"} |
      | {} |
