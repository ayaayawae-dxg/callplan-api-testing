Feature: Get Outlet

  @smoke
  Scenario: Successfully get outlet
    When I send POST request to get-outlet with payload:
      """
      {
        "rayonCode": "TGRVIC0101",
        "personCode": "CRM990808003223",
        "companyId": 1
      }
      """
    Then the response status code should be 200
    And the response should contain "success" with value "true"
    And the response should contain array of outlet data
    And each outlet data should match schema
  
  @negative
  Scenario Outline: Get Outlet with invalid value
    When I send POST request to get-outlet with payload:
      """
        <payload>
      """
    Then the response status code should be 200
    And the response should contain "success" with value "true"
    And the response should contain empty array of outlet data
    Examples:
      | payload |
      | {"rayonCode": "INVALID RAYON CODE", "personCode": "CRM990808003223", "companyId": 1} |
      | {"rayonCode": "TGRVIC0101", "personCode": "INVALID PERSON CODE", "companyId": 1} |
      | {"rayonCode": "TGRVIC0101", "personCode": "CRM990808003223", "companyId": 999999999} |

  @negative
  Scenario Outline: Get Outlet with missing required field
    When I send POST request to get-outlet with payload:
      """
        <payload>
      """
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message
    Examples:
      | payload |
      | {"rayonCode": "TGRVIC0101", "personCode": "CRM990808003223"} |
      | {"rayonCode": "TGRVIC0101", "companyId": 1} |
      | {"personCode": "CRM990808003223", "companyId": 1} |
