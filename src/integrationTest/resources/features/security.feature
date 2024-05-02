Feature: User can log in

  Scenario: Administrator can login
    When "administrator" logs in
    Then login details are available
