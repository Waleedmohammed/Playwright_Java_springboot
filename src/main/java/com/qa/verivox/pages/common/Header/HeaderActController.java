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

}
