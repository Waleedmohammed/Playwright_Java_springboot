package com.qa.verivox.pages.common.Home;

import com.qa.verivox.core.factory.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HomePage {
    private HomeActController act ;
    private HomeVerifyController verify ;
    protected BasePage page;

    HomePage(BasePage page) {
        this.page = page;
    }

    // it is private constructor to force getting page object via static method getHomePage
    private HomePage(BasePage page, HomeActController act, HomeVerifyController verify) {
        this.page = page;
        this.act = act;
        this.verify = verify;
    }


    protected  String acceptCookies = "id=L2AGLb";

    protected  String searchText = "id=APjFqb";

    protected  String searchBtn = "//input[@class='gNO89b']";

    public static HomePage getHome(BasePage page) {
        return new HomePage(page, new HomeActController(page), new HomeVerifyController(page));
    }

    public HomeActController act() {
        return act;
    }

    public HomeVerifyController verify() {
        return verify;
    }

}
