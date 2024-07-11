package com.qa.verivox.DSL;

import com.qa.verivox.pages.Home.HomePage;
import com.qa.verivox.pages.common.Header.HeaderPage;
import com.qa.verivox.pages.common.SearchResult.SearchPage;
import com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.qa.verivox.pages.Home.HomePage.getHomePage;
import static com.qa.verivox.pages.common.Header.HeaderPage.getHeader;
import static com.qa.verivox.pages.common.SearchResult.SearchPage.getSearchPage;
import static com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage.getprivathaftpflichtPage;

@Slf4j
public class SearchTarrifTest extends TestBase{

    HomePage home;
    HeaderPage header;
    PrivathaftpflichtPage privathaftpflichtPage;
    SearchPage searchPage;

    @BeforeMethod
    public void beforeEach() throws Exception {

        home = getHomePage(super.driver);
        home.act()
                .acceptCookies();

        header = getHeader(super.driver);
        header.act()
                .mouseHoverVersicherungTab()
                .goToPrivathaftpflichtPage();

        privathaftpflichtPage = getprivathaftpflichtPage(super.driver);
        privathaftpflichtPage.act()
                .selectFamilistand("Familie ohne Kinder")
                .enterAge(36)
                .clickJetztVergleichenButton()
                .enterBirthDate("21.09.1988")
                .enterPostalCode("13088")
                .clickOnVergleichenButton();

        searchPage = getSearchPage(super.driver);
        searchPage.verify()
                .tarrifsShownSuccessfully();
    }

    @Test
    public void test_load_multiple_tariff_result_pages() throws Exception {

        searchPage = getSearchPage(super.driver);
        searchPage.verify()
                .totalNumberOfResultsDisplayed();

        searchPage.act()
                .scrollTillEndOfPage();

        searchPage.verify()
                .isSearchResultCount(appProperties.getSearchresults());

        searchPage.act()
                .clickLoadMoreButton();

        searchPage.verify()
                .isSearchResultCount(appProperties.getSearchresults())
                .iCanClickLoadMoreButtonTillAllProductsDisplayed(appProperties.getSearchresults());

    }

    @Test
    public void test_offer_details_for_selected_tariff() throws Exception {

        List<String> productDetails1 = Arrays.asList("Wichtigste Leistungen", "Allgemein", "Tätigkeiten und Hobbys");
        List<String> productDetails2 = Arrays.asList("Miete & Immobilien", "Dokumente");


        searchPage = getSearchPage(super.driver);
        searchPage.verify()
                .iCanSeePriceOfProductNumber(1);

        searchPage.act()
                .clickOnTarifDetailsButton(1);

        searchPage.verify()
                .iCanSeeProductDetails(productDetails1)
                .iCanSeeProductDetails(productDetails2)
                .onlineAntragButtonDisplayed(1);

    }

}
