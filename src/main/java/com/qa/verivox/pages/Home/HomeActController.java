package com.qa.verivox.pages.Home;

import com.qa.verivox.core.driverUtils.Driver;
import org.openqa.selenium.Keys;

import java.time.Duration;

public class HomeActController extends HomePage {

    HomeActController(Driver driver) {
        super(driver);
    }

    public HomeActController acceptCookies() throws Exception {
        driver.waitForVisibilityOfElementLocated(cookiesPanel);
        driver.findElement(acceptCookiesBtn).click();
        return this;
    }

}
