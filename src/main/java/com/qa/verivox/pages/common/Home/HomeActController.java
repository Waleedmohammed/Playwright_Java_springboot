package com.qa.verivox.pages.common.Home;

import com.qa.verivox.core.factory.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HomeActController extends HomePage {

    HomeActController(BasePage page) {
        super(page);
    }

    public HomeActController getTitle() {
        page.getTitle();
        return this;
    }

    public HomeActController acceptCookies() throws Exception {
        page.clickOn(acceptCookies);
        return this;
    }

    public HomeActController enterSearchText(String text) throws Exception {
        page.typeIn(searchText, text);
        return this;
    }

    public void clickSearchBtn() throws Exception {
        page.clickOn(searchBtn);
    }
}
