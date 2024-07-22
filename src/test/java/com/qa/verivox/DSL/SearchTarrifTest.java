package com.qa.verivox.DSL;

import com.qa.verivox.pages.common.Header.HeaderPage;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;


import static com.qa.verivox.pages.common.Header.HeaderPage.getHeader;


@Slf4j
public class SearchTarrifTest extends TestBase{

    HeaderPage home;

    @Test
    public void TestSearch() throws Exception {

        home = getHeader(super.page);
        home.act()
                .getTitle()
                .acceptCookies()
                .enterSearchText("Test Plaz")
                .clickSearchBtn();
    }



}
