Feature: Twitter Profile Section Update

  Scenario: Twitter Profile page update
    Given User is logged to Twitter
    When User is navigated to Edit Profile page
    ######### Provide the Profile picture in ProfilePicture folder in Project root directory
    Then Upload Profile Picture
    Then Update the BIO field as "Selenium Automation user"
    Then Update the Location field as "Delhi, India"
    Then Update Website field as "twitter.com"
    Then Save the Profile page
    Then Revisit the profile page and revalidate the submitted value "Selenium Automation user" "Delhi, India" "twitter.com"
    Then Logout