Feature: Get Master list

  @smoke
  Scenario: Successfully get master list data
    When I send POST request to "/get-masterlist" with payload:
      | rayonCode  | TGRVIC0101 |
      | companyId  | 1         |
    Then the response status code should be 200
    And the response should contain "success" with value "true"
    And the response should contain array of master list data
    And each master list data should match schema

   @negative
   Scenario: Get master list with invalid rayon code
     When I send POST request to "/get-masterlist" with payload:
       | rayonCode  | INVALID123 |
       | companyId  | 1          |
     Then the response status code should be 200
     And the response should contain "success" with value "true"
     And the response should contain empty array of master list data

   @negative
   Scenario: Get master list with invalid company ID
     When I send POST request to "/get-masterlist" with payload:
       | rayonCode  | TGRVICs01 |
       | companyId  | 999       |
     Then the response status code should be 200
     And the response should contain "success" with value "true"
     And the response should contain empty array of master list data

   @negative
   Scenario: Get master list without required fields
     When I send POST request to "/get-masterlist" with payload:
       |  |
     Then the response status code should be 200
     And the response should contain "success" with value "false"
     And the response should contain error message