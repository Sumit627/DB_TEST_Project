Feature: Twitter_Login_functionality

  Scenario: Test Login field validation.
    Given User is navigate to Twitter Landing page
    Then Click on Login button
    Then Login page is displayed
    Then Verify that Login button is disabled if user provide only Username details
    Then Verify that Login button is disabled if user provide only Password details
    Then Verify that Login fails if user provide both Username and Password details incorrect
    Then Verify error message "Please double-check and try again."

  Scenario: Twitter login success
    Given User is navigate to Twitter Landing page
    Then Click on Login button
    Then Login page is displayed
    Then user provide both Username and Password details correctly
    And Verify that Twitter Homepage is displayed
    Then Logout