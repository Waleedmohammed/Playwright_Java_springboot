package com.qa.verivox.DSL;

import com.qa.verivox.pages.common.Header.HeaderPage;
import com.qa.verivox.pages.common.Home.HomePage;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;


import static com.qa.verivox.pages.common.Header.HeaderPage.getHeader;
import static com.qa.verivox.pages.common.Home.HomePage.getHome;


@Slf4j
public class SearchTarrifTest extends TestBase {

    HomePage home;

    HeaderPage headerPage;

    @Test
    public void TestSearch() throws Exception {

        home = getHome(super.basePage);
        headerPage = getHeader(super.basePage);
        home.act()
                .getTitle()
                .acceptCookies()
                .enterSearchText("Test Play")
                .clickSearchBtn();

        headerPage.verify()
                .googleIconDisplayed();
    }


}
