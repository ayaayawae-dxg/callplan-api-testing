#Feature: Save Best Time Visit
#
#  @smoke
#  Scenario: Successfully save best time visit data
#    When I send POST request to save-best-time-visit with payload:
#      """
#      {
#        "rayonId": 2952,
#        "rayonCode": "TGRVIC0101",
#        "personId": 37697,
#        "personCode": "CRM130912003798",
#        "email": "bonaventura.sanjaya@dexagroup.com",
#        "isUpcountry": 0,
#        "details": [
#          {
#            "location": "42 SOLO FARMA, APT",
#            "customerId": 197176,
#            "customerName": "42 SOLO FARMA, APT",
#            "customerLatitude": "-6.25092380",
#            "customerLongitude": "106.57150480",
#            "mondayDay": false,
#            "mondayNight": true,
#            "tuesdayDay": false,
#            "tuesdayNight": true,
#            "wednesdayDay": false,
#            "wednesdayNight": false,
#            "thursdayDay": false,
#            "thursdayNight": false,
#            "fridayDay": false,
#            "fridayNight": false
#          },
#          {
#            "location": "42 SILOAM HEALTH CARE TBK, PT.",
#            "customerId": 11964,
#            "customerName": "42 SILOAM HEALTH CARE TBK, PT.",
#            "customerLatitude": "-6.22540220",
#            "customerLongitude": "106.59800180",
#            "mondayDay": false,
#            "mondayNight": false,
#            "tuesdayDay": false,
#            "tuesdayNight": false,
#            "wednesdayDay": false,
#            "wednesdayNight": false,
#            "thursdayDay": false,
#            "thursdayNight": false,
#            "fridayDay": false,
#            "fridayNight": false
#          },
#          {
#            "location": "42 MELATI,RSIA",
#            "customerId": 70321,
#            "customerName": "42 MELATI,RSIA",
#            "customerLatitude": "-6.17770390",
#            "customerLongitude": "106.62502520",
#            "mondayDay": false,
#            "mondayNight": false,
#            "tuesdayDay": false,
#            "tuesdayNight": false,
#            "wednesdayDay": false,
#            "wednesdayNight": false,
#            "thursdayDay": false,
#            "thursdayNight": false,
#            "fridayDay": false,
#            "fridayNight": false
#          }
#        ]
#      }
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "true"
#
#  @negative
#  Scenario: Save best time visit data with missing details
#    When I send POST request to save-best-time-visit with payload:
#      """
#      {
#        "rayonId": 2952,
#        "rayonCode": "TGRVIC0101",
#        "personId": 37697,
#        "personCode": "CRM130912003798",
#        "email": "bonaventura.sanjaya@dexagroup.com",
#        "isUpcountry": 0
#      }
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "false"
#    And the response should contain error message
#
#  @negative
#  Scenario: Save best time visit data with empty details
#    When I send POST request to save-best-time-visit with payload:
#      """
#      {
#        "rayonId": 2952,
#        "rayonCode": "TGRVIC0101",
#        "personId": 37697,
#        "personCode": "CRM130912003798",
#        "email": "bonaventura.sanjaya@dexagroup.com",
#        "isUpcountry": 0,
#        "details": []
#      }
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "false"
#    And the response should contain error message
#
#  @negative
#  Scenario: Save best time visit data with duplicate time schedule
#    When I send POST request to save-best-time-visit with payload:
#      """
#      {
#        "rayonId": 2952,
#        "rayonCode": "TGRVIC0101",
#        "personId": 37697,
#        "personCode": "CRM130912003798",
#        "email": "bonaventura.sanjaya@dexagroup.com",
#        "isUpcountry": 0,
#        "details": [
#          {
#            "location": "42 SOLO FARMA, APT",
#            "customerId": 197176,
#            "customerName": "42 SOLO FARMA, APT",
#            "customerLatitude": "-6.25092380",
#            "customerLongitude": "106.57150480",
#            "mondayDay": false,
#            "mondayNight": true,
#            "tuesdayDay": false,
#            "tuesdayNight": true,
#            "wednesdayDay": false,
#            "wednesdayNight": false,
#            "thursdayDay": false,
#            "thursdayNight": false,
#            "fridayDay": false,
#            "fridayNight": false
#          },
#          {
#            "location": "42 SILOAM HEALTH CARE TBK, PT.",
#            "customerId": 11964,
#            "customerName": "42 SILOAM HEALTH CARE TBK, PT.",
#            "customerLatitude": "-6.22540220",
#            "customerLongitude": "106.59800180",
#            "mondayDay": false,
#            "mondayNight": true,
#            "tuesdayDay": false,
#            "tuesdayNight": false,
#            "wednesdayDay": false,
#            "wednesdayNight": false,
#            "thursdayDay": false,
#            "thursdayNight": false,
#            "fridayDay": false,
#            "fridayNight": false
#          },
#          {
#            "location": "42 MELATI,RSIA",
#            "customerId": 70321,
#            "customerName": "42 MELATI,RSIA",
#            "customerLatitude": "-6.17770390",
#            "customerLongitude": "106.62502520",
#            "mondayDay": false,
#            "mondayNight": true,
#            "tuesdayDay": false,
#            "tuesdayNight": false,
#            "wednesdayDay": false,
#            "wednesdayNight": false,
#            "thursdayDay": false,
#            "thursdayNight": false,
#            "fridayDay": false,
#            "fridayNight": false
#          }
#        ]
#      }
#      """
#    Then the response status code should be 200
#    And the response should contain "success" with value "false"
#    And the response should contain error message
#
