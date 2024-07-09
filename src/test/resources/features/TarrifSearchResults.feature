Feature: Tariff search pages

  Background:
    Given I applied tariff calculation criteria with age : 36 , Birthdate : "21.09.1988" , Familiestand : "Familie ohne Kinder" and PostalCode : "13088"

  @Search
  Scenario:Load multiple tariff result pages
    Given I display the tariff Result List page
    Then I should see the total number of available tariffs listed above all the result list
    When I scroll to the end of the result list page
    Then I should see only the first configured results count tariffs displayed
    When I click on the button labeled 20 weitere Tarife laden
    Then I should see the next configured results count tariffs displayed
    And I can continue to load any additional tariffs until all tariffs have been displayed


  @Search
  Scenario:Verify offer details for a selected tariff
    Given I display the tariff Result List page
    Then I should see the tariff price of the 1 tariff
    When I open tariff 1 details
    Then I see tariff details sections:
      | Wichtigste Leistungen |
      | Allgemein |
      | Tätigkeiten und Hobbys |
    And I see tariff details sections:
      | Miete & Immobilien |
      | Dokumente |
    And I see the ZUM ONLINE-ANTRAG button for tariff 1
