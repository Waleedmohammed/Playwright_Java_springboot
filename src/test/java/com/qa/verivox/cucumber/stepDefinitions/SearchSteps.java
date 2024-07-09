package com.qa.verivox.cucumber.stepDefinitions;

import com.qa.verivox.core.conf.AppProperties;
import com.qa.verivox.core.driverUtils.Driver;
import com.qa.verivox.core.driverUtils.DriverManager;
import com.qa.verivox.pages.Home.HomePage;
import com.qa.verivox.pages.common.Header.HeaderPage;
import com.qa.verivox.pages.common.SearchResult.SearchPage;
import com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.qa.verivox.pages.Home.HomePage.getHomePage;
import static com.qa.verivox.pages.common.Header.HeaderPage.getHeader;
import static com.qa.verivox.pages.common.SearchResult.SearchPage.getSearchPage;
import static com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage.getprivathaftpflichtPage;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchSteps {
    @Autowired
    protected AppProperties appProperties;

    @Autowired
    protected DriverManager driverManager;

    public Driver driver;

    public int configuredSearchResults;

    HomePage homePage;
    HeaderPage header;
    PrivathaftpflichtPage privathaftpflichtPage;
    SearchPage searchPage;

    @Before("@Search")
    public void setUp() {
        configuredSearchResults = appProperties.getSearchresults();
        log.info("I have *******    " + configuredSearchResults);
        driver = driverManager.getDriver();
        driver.start();
        driver.open(appProperties.getAppUrl());
        driver.maximize();

    }

    @After("@Search")
    public void teardown(Scenario scenario) {
        scenario.attach(driver.captureScreenshot("Search"), "image/png", scenario.getName());
        driver.quit();
        driverManager.quitAllDrivers();
        driver.stopDriverService();
    }

    @Given("I applied tariff calculation criteria with age : {int} , Birthdate : {string} , Familiestand : {string} and PostalCode : {string}")
    public void iAppliedTariffCalculationCriteriaWithAgeBirthdateFamiliestandAndPostalCode(int age, String birthDate, String familiestand, String postalcode) throws Exception {

        homePage = getHomePage(driver);
        homePage.act()
                .acceptCookies();

        header = getHeader(driver);
        header.act()
                .mouseHoverVersicherungTab()
                .goToPrivathaftpflichtPage();

        privathaftpflichtPage = getprivathaftpflichtPage(driver);
        privathaftpflichtPage.act()
                .selectFamilistand(familiestand)
                .enterAge(age)
                .clickJetztVergleichenButton()
                .enterBirthDate(birthDate)
                .enterPostalCode(postalcode)
                .clickOnVergleichenButton();

    }

    @Given("I display the tariff Result List page")
    public void iDisplayTheTariffResultListPage() throws Exception {
        searchPage = getSearchPage(driver);
        searchPage.verify()
                .tarrifsShownSuccessfully();
    }

    @Then("I should see the total number of available tariffs listed above all the result list")
    public void iShouldSeeTheTotalNumberOfAvailableTariffsListedAboveAllTheResultList() throws Exception {
        searchPage.verify()
                .totalNumberOfResultsDisplayed();
    }

    @When("I scroll to the end of the result list page")
    public void iScrollToTheEndOfTheResultListPage() throws Exception {
        searchPage.act()
                .scrollTillEndOfPage();

    }

    @Then("I should see only the first configured results count tariffs displayed")
    public void iShouldSeeOnlyTheFirstTariffsDisplayed() throws Exception {
        searchPage.verify()
                .isSearchResultCount(configuredSearchResults);
    }

    @When("I click on the button labeled 20 weitere Tarife laden")
    public void iClickOnTheButtonLabeledWeitereTarifeLaden() throws Exception {
        searchPage.act()
                .clickLoadMoreButton();
    }

    @Then("I should see the next configured results count tariffs displayed")
    public void iShouldSeeTheNextTariffsDisplayed() throws Exception {
        searchPage.verify()
                .isSearchResultCount(configuredSearchResults);
    }

    @And("I can continue to load any additional tariffs until all tariffs have been displayed")
    public void iCanContinueToLoadAnyAdditionalTariffsUntilAllTariffsHaveBeenDisplayed() throws Exception {
        searchPage.verify()
                .iCanClickLoadMoreButtonTillAllProductsDisplayed(configuredSearchResults);
    }


    @Then("I should see the tariff price of the {int} tariff")
    public void iShouldSeeTheTariffPriceOfTheTariff(int productNumber) throws Exception {
        searchPage.verify()
                .iCanSeePriceOfProductNumber(productNumber);

    }

    @When("I open tariff {int} details")
    public void iOpenTariffDetails(int productNumber) throws Exception {
        searchPage.act()
                .clickOnTarifDetailsButton(productNumber);
    }

    @Then("I see tariff details sections:")
    public void iSeeTariffDetails(List<String> productDetails) throws Exception {
        searchPage.verify()
                .iCanSeeProductDetails(productDetails);
    }


    @And("I see the ZUM ONLINE-ANTRAG button for tariff {int}")
    public void iSeeTheZUMONLINEANTRAGButton(int productNumber) {
        searchPage.verify()
                .onlineAntragButtonDisplayed(productNumber);
    }

}
