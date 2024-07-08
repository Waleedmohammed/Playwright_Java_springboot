Feature: Privathaftpflicht calculator

AS A Verivox user
I WANT TO use the Privathaftpflicht calculator and tariff search pages
SO THAT I can select the best available tariff based on price

  @Calculator
  Scenario Outline: Verify the DSL calculator
    Given that I can open www.verivox.de
    When I navigate to Versicherungen and select Privathaftpflicht
    And I enter age as <age> and select Familiestand as <familiestand>
    Then I go to the Privathaftpflicht personal information page
    And I enter my birthdate <birthDate>
    And I enter my zip code <postalCode>
    And I click the Jetzt vergleichen button
    Then I should see a page that lists the available tariffs for my selection
    Examples:
      | age | familiestand           | birthDate    | postalCode |
      | 36  | "Familie ohne Kinder"  |"21.09.1988"  | "13088"    |
