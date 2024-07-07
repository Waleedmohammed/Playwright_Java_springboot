package com.qa.verivox.pages.Home;

import com.qa.verivox.core.driverUtils.Driver;
import org.openqa.selenium.Keys;

public class HomeActController extends HomePage {

    HomeActController(Driver driver) {
        super(driver);
    }

    public HomeActController acceptCookies() throws Exception {
        driver.findElement(acceptCookiesBtn).click();
        return this;
    }

}
