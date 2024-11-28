Feature: Get Feedback Question

  @smoke
  Scenario: Successfully get feedback question
    When I send GET request to feedback-question with query parameter:
      | weekNumber | 1 |
    Then the response status code should be 200
    And the response should contain "success" with value "true"
    And the response should contain array of feedback question data
    And each feedback question data should match schema

  @negative
  Scenario Outline: Get feedback question with invalid week number
    When I send GET request to feedback-question with query parameter:
      | weekNumber | <weekNumber> |
    Then the response status code should be 200
    And the response should contain "success" with value "true"
    And the response should contain empty array of feedback question data
    Examples:
      | weekNumber |
      | 999999999  |
      | -1         |
      | 0          |
      | 1.5        |
      | asdasdasd  |

  @negative
  Scenario: Get feedback question with missing week number
    When I send GET request to feedback-question with query parameter:
      | |
    Then the response status code should be 200
    And the response should contain "success" with value "false"
    And the response should contain error message
