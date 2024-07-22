package com.qa.verivox.pages.Home;

import com.qa.verivox.core.driverUtils.Driver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

@Slf4j
public class HomePage extends LoadableComponent<HomePage> {

    private HomeActController act;
    private HomeVerifyController verify;

    protected Driver driver;

    protected By cookiesPanel = By.xpath("//div[@class='cmp-container first-load']");
    protected By acceptCookiesBtn = By.id("uc-btn-accept-banner");


    HomePage(Driver driver) {
        this.driver = driver;
    }

    // it is private constructor to force getting page object via static method getHomePage
    private HomePage(Driver driver, HomeActController act, HomeVerifyController verify) {
        this.driver = driver;
        this.act = act;
        this.verify = verify;
        PageFactory.initElements(driver.getWebDriver(), this);
    }

    public static HomePage getHomePage(Driver driver) {
        return new HomePage(driver, new HomeActController(driver), new HomeVerifyController(driver));
    }

    public HomeActController act() {
        return act;
    }

    public HomeVerifyController verify() {
        return verify;
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        Assertions.assertAll(
                () -> driver.findElement(acceptCookiesBtn).verifyDisplayed(true)
        );
        log.info("Main Page Loaded Successfully");
    }
}
