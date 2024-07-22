package com.qa.verivox.DSL;

import com.qa.verivox.pages.Home.HomePage;
import com.qa.verivox.pages.common.Header.HeaderPage;
import com.qa.verivox.pages.common.SearchResult.SearchPage;
import com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.qa.verivox.pages.Home.HomePage.getHomePage;
import static com.qa.verivox.pages.common.Header.HeaderPage.getHeader;
import static com.qa.verivox.pages.common.SearchResult.SearchPage.getSearchPage;
import static com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage.getprivathaftpflichtPage;

@Slf4j
public class DSLCalculatorTest extends TestBase {

    HomePage home;
    HeaderPage headerPage;
    PrivathaftpflichtPage privathaftpflichtPage;
    SearchPage searchPage;

    @Test
    public void Verify_DSL_Calculator() throws Exception {

        // Home Page Test Steps //
        home = getHomePage(super.driver)
                .act()
                .acceptCookies();

        // Header Test Steps //
        headerPage = getHeader(super.driver)
                .act()
                .mouseHoverVersicherungTab()
                .goToPrivathaftpflichtPage();

        // privathaftpflichtPage Test Steps & Verifications//
        privathaftpflichtPage = getprivathaftpflichtPage(super.driver);

        privathaftpflichtPage.act()
                .selectFamilistand("Familie ohne Kinder")
                .enterAge(36)
                .clickJetztVergleichenButton();

        privathaftpflichtPage
                .verify()
                .familiestandDdlInFormIsDisplayed()
                .geburtsdatumIsDisplayed();

        privathaftpflichtPage
                .act()
                .enterBirthDate("21.09.1988")
                .enterPostalCode("13088")
                .clickOnVergleichenButton();

        // Search Result Page Verifications//
        searchPage = getSearchPage(super.driver)
                .verify()
                .tarrifsShownSuccessfully();

    }
}
