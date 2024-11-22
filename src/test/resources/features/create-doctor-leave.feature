Feature: Create Doctor Leave

  @smoke
  Scenario: Successfully create doctor leave data
    Given Leave period is not set yet for the selected date:
      | leaveStart | 2024-02-02 |
      | leaveEnd   | 2024-02-05 |
      | personId   | 37697      |
      | rayonCode  | TGRVIC0101 |
    When I send POST request to create-doctor-leave with payload:
       """
       {
         "rayonId": 2952,
         "rayonCode": "TGRVIC0101",
         "personId": 37697,
         "personCode": "CRM130912003798",
         "leaveStart": "2024-02-02",
         "leaveEnd": "2024-02-05",
         "notes": "",
         "insertedBy": "bonaventura.sanjaya@dexagroup.com"
       }
       """
     Then the response status code should be 200
     And the response should contain "success" with value "true"

  @negative
  Scenario Outline: Create doctor leave with overlapping leave period - <description>
    Given Leave period is already set for the selected date:
      | leaveStart | <leaveStart> |
      | leaveEnd   | <leaveEnd>   |
      | personId   | 37697        |
      | rayonCode  | TGRVIC0101   |
    When I send POST request to create-doctor-leave with payload:
      """
      {
        "rayonId": 2952,
        "rayonCode": "TGRVIC0101",
        "personId": 37697,
        "personCode": "CRM130912003798",
        "leaveStart": "2024-02-02",
        "leaveEnd": "2024-02-05",
        "notes": "",
        "insertedBy": "bonaventura.sanjaya@dexagroup.com"
      }
      """
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message
    Examples:
      | leaveStart | leaveEnd   | description                                               |
      | 2024-02-01 | 2024-02-03 | Overlapping with start date of leave period               |
      | 2024-02-03 | 2024-02-10 | Overlapping with end date of leave period                 |
      | 2024-02-02 | 2024-02-05 | Same leave period                                         |
      | 2024-02-01 | 2024-02-15 | Overlapping with both start and end date of leave period  |

  @negative
  Scenario Outline: Create doctor leave with invalid date format
    When I send POST request to create-doctor-leave with payload:
      """
      {
        "rayonId": 2952,
        "rayonCode": "TGRVIC0101",
        "personId": 37697,
        "personCode": "CRM130912003798",
        "leaveStart": "<leaveStart>",
        "leaveEnd": "<leaveEnd>",
        "notes": "",
        "insertedBy": "bonaventura.sanjaya@dexagroup.com"
      }
      """
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message
    Examples:
      | leaveStart | leaveEnd   |
      | 02-02-2024 | 05-02-2024 |
      | asdasdasda | asdazxzxcz |

  @negative
  Scenario: Create doctor leave with leave start date is greater than leave end date
    Given Leave period is not set yet for the selected date:
      | leaveStart | 2024-02-20 |
      | leaveEnd   | 2024-02-22 |
      | personId   | 37697      |
      | rayonCode  | TGRVIC0101 |
    When I send POST request to create-doctor-leave with payload:
      """
      {
        "rayonId": 2952,
        "rayonCode": "TGRVIC0101",
        "personId": 37697,
        "personCode": "CRM130912003798",
        "leaveStart": "2024-02-22",
        "leaveEnd": "2024-02-20",
        "notes": "",
        "insertedBy": "bonaventura.sanjaya@dexagroup.com"
      }
      """
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message