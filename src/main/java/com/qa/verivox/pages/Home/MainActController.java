package com.qa.verivox.pages.Home;

import com.qa.verivox.core.driverUtils.Driver;
import org.openqa.selenium.Keys;

public class MainActController extends MainPage {

    MainActController(Driver driver) {
        super(driver);
    }


    public MainActController enterSearchText(String text) throws Exception {
        driver.findElement(searchTxtBox).enterText(text);
        driver.doAction(Keys.ESCAPE).perform();
        return this;
    }

    public MainActController acceptCookies() throws Exception {
        driver.findElement(acceptCookies).click();
        return this;
    }

    public void clickSearchBtn() throws Exception {
        driver.findElement(searchBtn).click();
    }
}
