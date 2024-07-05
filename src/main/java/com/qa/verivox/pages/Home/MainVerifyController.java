package com.qa.verivox.pages.Home;

import com.qa.verivox.core.driverUtils.Driver;
import org.testng.Assert;

public class MainVerifyController extends MainPage{
    MainVerifyController(Driver driver) {
        super(driver);
    }

    public void headerIsExist() {
        Assert.assertTrue(driver.findElement(logo).isDisplayed());
    }
}
