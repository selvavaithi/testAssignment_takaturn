Feature: Login Feature of Takaturn
  Validate User is able to login Takaturn

  @SmokeTest @sanity
  Scenario: Verify User is able to login to Takaturn
    #Desc: User open the url
    Given User visit Takaturn Home page
    And User clicks on "Log In" button
    When User login Takaturn using valid credentials
    Then User verify that he is logged in
