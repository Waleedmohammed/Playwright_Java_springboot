package com.qa.verivox.pages.common.Header;

import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderPage {
    private HeaderActController act ;
    private HeaderVerifyController verify ;

    protected Page page;

    HeaderPage(Page page) {
        this.page = page;
    }

    // it is private constructor to force getting page object via static method getHomePage
    private HeaderPage(Page driver, HeaderActController act, HeaderVerifyController verify) {
        this.page = driver;
        this.act = act;
        this.verify = verify;
    }


    protected  String acceptCookies = "id=L2AGLb";

    protected  String searchText = "id=APjFqb";

    protected  String searchBtn = "//input[@class='gNO89b']";

    public static HeaderPage getHeader(Page page) {
        return new HeaderPage(page, new HeaderActController(page), new HeaderVerifyController(page));
    }

    public HeaderActController act() {
        return act;
    }

    public HeaderVerifyController verify() {
        return verify;
    }

}
