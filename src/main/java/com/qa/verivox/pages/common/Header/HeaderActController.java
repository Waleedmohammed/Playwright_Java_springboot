package com.qa.verivox.pages.common.Header;

import com.qa.verivox.core.factory.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderActController extends HeaderPage {

    HeaderActController(BasePage page) {
        super(page);
    }

    public HeaderActController getTitle() {
        page.getTitle();
        return this;
    }

    public HeaderActController acceptCookies() throws Exception {
        page.clickOn(acceptCookies);
        return this;
    }

    public HeaderActController enterSearchText(String text) throws Exception {
        page.typeIn(searchText, text);
        return this;
    }

    public HeaderActController clickSearchBtn() throws Exception {
        page.clickOn(searchBtn);
        return this;
    }
}
