@smoke_test @debug_test
Feature: User Login
  As a Security Auditor
  I want to ensure that technical capabilities are present
  So that they can be used throughout application

  Background:
    Given The feature flag "debug_test_security" is "enabled"

  Scenario: On first visit, on lack of session, redirect to login page
    Given I am not logged in
    And This is the first time that I access the application
    When I try to access a "debug security page that requires authentication"
    Then I should be redirected to login page

  Scenario: On subsequent visit, on lack of session, propose a redirection to login page
    Given I am not logged in
    And This is not the first time that I access the application
    When I try to access a "debug security page that requires authentication"
    Then I should be presented with an error page informing me about lack of authentication
    And I should be presented with an option to redirect to login page

  Scenario: On login, redirect back to target page after log in
    Given I am not logged in
    And I try to access a "debug security page that requires authentication"
    And I should be redirected to login page
    When I log in as "Any user"
    Then I should be redirected back to "debug security page that requires authentication"
    And The secure page should be available

  Scenario Outline: Access to component with missing authority
    Given I am logged in as "<role>"
    When I try to access a "debug security page that requires <authorities>"
    Then I should be presented with an error page informing me about lack of authority
    And I should be presented with an option to go back
    And I should be presented with an option to go to the main page

    Examples:
      | role      | authorities |
      | Read Only |             |

  Scenario: Access to subcomponent with missing authority
    Given I am logged in as "<role>"
    When I try to access a "debug security page that requires <authority>"
    Then I should be presented with an error page
    And I should be presented with an option to go back
    And I should be presented with an option to go to the main page




#  Scenario Outline: Redirect to login page
#    Given I access application without session
#    When I enter valid credentials for "<role>"
#    And I click the login button
#    Then I should be redirected to the dashboard
#
#  Scenario Outline: Successful login
#    Given I access application without session
#    When I enter valid credentials for "<role>"
#    And I click the login button
#    Then I should be redirected to the dashboard
#
#    Examples:
#      | role         |
#      | Administrator|
#      | Manager      |
#      | Read Only    |
#
#  Scenario Outline: Failed login
#    Given I am on the login page
#    When I enter invalid credentials for "<role>"
#    And I click the login button
#    Then I should see an error message
#
#    Examples:
#      | role         |
#      | Administrator|
#      | Manager      |
#      | Read Only    |
#
#  Scenario: Access as anonymous client
#    Given I am not logged in
#    When I try to access a restricted page
#    Then I should be redirected to the login page
