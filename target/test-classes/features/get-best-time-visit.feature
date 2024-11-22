#Feature: Get Best Time Visit
#
#  @smoke
#  Scenario: Successfully get best time visit data
#    Given the person already has best time visit data:
#      | rayonCode  | TGRVIC0101      |
#      | personId   | 37697           |
#      | personCode | CRM130912003798 |
#    When I send POST request to "/get-best-time-visit" with payload:
#      | rayonCode  | TGRVIC0101      |
#      | personId   | 37697           |
#      | personCode | CRM130912003798 |
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#    And the response should contain array of best time visit data
#    And each best time visit data should match the payload:
#      | PERSON_ID   | 37697           |
#      | PERSON_CODE | CRM130912003798 |
#      | RAYON_CODE  | TGRVIC0101      |
#
#  @negative
#  Scenario: Get best time visit with missing rayon code
#    When I send POST request to "/get-best-time-visit" with payload:
#      | personId   | 37697           |
#      | personCode | CRM130912003798 |
#    Then the response status code should be 200
#    And the response should contain "success" with value "false"
#    And the response should contain error message
#
#  @negative
#  Scenario: Get best time visit with invalid rayon code
#    When I send POST request to "/get-best-time-visit" with payload:
#      | rayonCode  | INVALID123      |
#      | personId   | 37697           |
#      | personCode | CRM130912003798 |
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#    And the response should contain empty array of best time visit data
#
#  @negative
#  Scenario: Get best time visit with invalid person id but valid person code
#    When I send POST request to "/get-best-time-visit" with payload:
#      | rayonCode  | TGRVIC0101      |
#      | personId   | 999999999       |
#      | personCode | CRM130912003798 |
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#    And the response should contain empty array of best time visit data
#
#  @negative
#  Scenario: Get best time visit with invalid person code but valid person id
#    When I send POST request to "/get-best-time-visit" with payload:
#      | rayonCode  | TGRVIC0101      |
#      | personId   | 37697           |
#      | personCode | INVALID123      |
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#    And the response should contain empty array of best time visit data
