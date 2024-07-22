package com.qa.verivox.pages.common.Header;

import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderActController extends HeaderPage {

    HeaderActController(Page page) {
        super(page);
    }

    public HeaderActController getTitle() throws Exception {
        String title = page.title();
        log.info("Title is {}", title);
        return this;
    }

    public HeaderActController acceptCookies() throws Exception {
        page.click(acceptCookies);
        return this;
    }

    public HeaderActController enterSearchText(String text) throws Exception {
        page.fill(searchText,text);
        return this;
    }

    public HeaderActController clickSearchBtn() throws Exception {
        page.click(searchBtn);
        return this;
    }
}
