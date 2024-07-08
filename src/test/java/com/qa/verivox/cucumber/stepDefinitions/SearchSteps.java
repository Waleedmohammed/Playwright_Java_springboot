package com.qa.verivox.cucumber.stepDefinitions;

import com.qa.verivox.core.conf.AppProperties;
import com.qa.verivox.core.driverUtils.Driver;
import com.qa.verivox.core.driverUtils.DriverManager;
import com.qa.verivox.pages.Home.HomePage;
import com.qa.verivox.pages.common.HeaderPage;
import com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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

import static com.qa.verivox.pages.Home.HomePage.getHomePage;
import static com.qa.verivox.pages.common.HeaderPage.getHeader;
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

    HomePage homePage;
    HeaderPage header;
    PrivathaftpflichtPage privathaftpflichtPage;
    @Before("@Search")
    public void setUp() {
        driver = driverManager.getDriver();
        driver.start();
        driver.open(appProperties.getAppUrl());
        driver.maximize();

    }

    @After("@Search")
    public void teardown() {
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
        privathaftpflichtPage.verify()
                .tarrifsShownSuccessfully();
    }

    @Then("I should see the total number of available tariffs listed above all the result list")
    public void iShouldSeeTheTotalNumberOfAvailableTariffsListedAboveAllTheResultList() {
    }

    @When("I scroll to the end of the result list page")
    public void iScrollToTheEndOfTheResultListPage() {
    }

    @Then("I should see only the first {int} tariffs displayed")
    public void iShouldSeeOnlyTheFirstTariffsDisplayed(int arg0) {
    }

    @When("I click on the button labeled {int} weitere Tarife laden")
    public void iClickOnTheButtonLabeledWeitereTarifeLaden(int arg0) {
    }

    @Then("I should see the next {int} tariffs displayed")
    public void iShouldSeeTheNextTariffsDisplayed(int arg0) {
    }

    @And("I can continue to load any additional tariffs until all tariffs have been displayed")
    public void iCanContinueToLoadAnyAdditionalTariffsUntilAllTariffsHaveBeenDisplayed() {
    }
}
