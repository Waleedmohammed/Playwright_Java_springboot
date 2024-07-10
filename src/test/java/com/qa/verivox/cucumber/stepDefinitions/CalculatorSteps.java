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

import static com.qa.verivox.pages.Home.HomePage.getHomePage;
import static com.qa.verivox.pages.common.Header.HeaderPage.getHeader;
import static com.qa.verivox.pages.common.SearchResult.SearchPage.getSearchPage;
import static com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage.getprivathaftpflichtPage;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalculatorSteps {

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    protected DriverManager driverManager;

    public Driver driver;

    HomePage homePage;
    HeaderPage header;
    PrivathaftpflichtPage privathaftpflichtPage;
    SearchPage searchPage;

    @Before("@Calculator")
    public void setUp() {
        driver = driverManager.getDriver();
        driver.start();
        driver.open(appProperties.getAppUrl());
    }

    @After("@Calculator")
    public void teardown(Scenario scenario) {
        scenario.attach(driver.captureScreenshot("Calculator"), "image/png", scenario.getName());
        driverManager.quitAllDrivers();
        driver.stopDriverService();
    }


    @Given("that I can open www.verivox.de")
    public void that_i_can_open_www_verivox_de() throws Exception {

        homePage = getHomePage(driver);
        homePage.act().
                acceptCookies();
    }

    @When("I navigate to Versicherungen and select Privathaftpflicht")
    public void i_navigate_to_versicherungen_and_select_privathaftpflicht() throws Exception {
        header = getHeader(driver);
        header.act().mouseHoverVersicherungTab().goToPrivathaftpflichtPage();
    }

    @And("I enter age as {int} and select Familiestand as {string}")
    public void i_enter_my_age_and_single_ohne_kinder(int age, String familiestand) throws Exception {
        privathaftpflichtPage = getprivathaftpflichtPage(driver);
        privathaftpflichtPage.act().selectFamilistand(familiestand)
                .enterAge(age).clickJetztVergleichenButton();
    }

    @Then("I go to the Privathaftpflicht personal information page")
    public void i_go_to_the_privathaftpflicht_personal_information_page() throws Exception {
        privathaftpflichtPage.verify()
                .familiestandDdlInFormIsDisplayed()
                .geburtsdatumIsDisplayed();
    }

    @And("I enter my birthdate {string}")
    public void i_enter_my_birthdate(String birthDate) throws Exception {
        privathaftpflichtPage.act().enterBirthDate(birthDate);
    }

    @And("I enter my zip code {string}")
    public void i_enter_my_zip_code_use(String postalCode) throws Exception {
        privathaftpflichtPage.act().enterPostalCode(postalCode);
    }

    @Then("I click the Jetzt vergleichen button")
    public void i_click_the_jetzt_vergleichen_button() throws Exception {
        privathaftpflichtPage.act().clickOnVergleichenButton();
    }

    @Then("I should see a page that lists the available tariffs for my selection")
    public void i_should_see_a_page_that_lists_the_available_tariffs_for_my_selection() throws Exception {
        searchPage = getSearchPage(driver);
        searchPage.verify().tarrifsShownSuccessfully();
    }

}
