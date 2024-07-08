Feature: Tariff search pages

  Background:
    Given I applied tariff calculation criteria with age : 36 , Birthdate : "21.09.1988" , Familiestand : "Familie ohne Kinder" and PostalCode : "13088"

  @Search
  Scenario:Load multiple tariff result pages
    Given I display the tariff Result List page
    Then I should see the total number of available tariffs listed above all the result list
    When I scroll to the end of the result list page
    Then I should see only the first 20 tariffs displayed
    When I click on the button labeled 20 weitere Tarife laden
    Then I should see the next 20 tariffs displayed
    And I can continue to load any additional tariffs until all tariffs have been displayed


  @Search
  Scenario:Verify offer details for a selected tariff
    Given I display the tariff Result List page
    Then I should see the tariff price of the first tariff
    When I open tariff details
    Then I see tariff details sections: “Weitere Leistungen”, “Allgemein“, „ Tätigkeiten und Hobbys“
    And I see tariff details sections: “Miete & Immobilien” and “Dokumente”
    And I see the ZUM ONLINE-ANTRAG button
