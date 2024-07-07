package com.qa.verivox.DSL;

import com.qa.verivox.pages.Home.HomePage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.qa.verivox.pages.Home.HomePage.getHomePage;

@Slf4j
public class TestClass extends TestBase {

    HomePage home;

    @Test
    public void searchWithString() throws Exception {

        home = getHomePage(super.driver)
                .act().acceptCookies();
//
//        home.act()
//                .acceptCookies()
//                .enterSearchText("Test Selenium")
//                .clickSearchBtn();
//
//        home.verify()
//                .headerIsExist();

        // Usage of AssertJ api
        /*List<String> test =new ArrayList<>();
        assertThat(test)
                .hasSize(0);*/
    }
}
