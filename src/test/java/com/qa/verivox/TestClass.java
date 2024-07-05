package com.qa.verivox;

import com.qa.verivox.pages.Home.MainPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.qa.verivox.pages.Home.MainPage.getHomePage;

@Slf4j
public class TestClass extends GuiTestBase {

    MainPage home;
    //= getHomePage(super.driver);

    @Test
    public void searchWithString() throws Exception {

//        home = getHomePage(super.driver);
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
