package com.qa.verivox.pages.common.Header;

import com.qa.verivox.core.factory.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderPage {
    private HeaderActController act ;
    private HeaderVerifyController verify ;
    protected BasePage page;

    HeaderPage(BasePage page) {
        this.page = page;
    }

    // it is private constructor to force getting page object via static method getHomePage
    private HeaderPage(BasePage page, HeaderActController act, HeaderVerifyController verify) {
        this.page = page;
        this.act = act;
        this.verify = verify;
    }


    protected  String googleIcon = "//img[@class='jfN4p']";

    public static HeaderPage getHeader(BasePage page) {
        return new HeaderPage(page, new HeaderActController(page), new HeaderVerifyController(page));
    }

    public HeaderActController act() {
        return act;
    }

    public HeaderVerifyController verify() {
        return verify;
    }

}
