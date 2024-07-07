package com.qa.verivox.pages.common;

import com.qa.verivox.core.driverUtils.Driver;
import com.qa.verivox.pages.Home.HomeActController;
import com.qa.verivox.pages.Home.HomePage;
import com.qa.verivox.pages.Home.HomeVerifyController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

@Slf4j
public class HeaderPage extends LoadableComponent<HeaderPage> {
    private HeaderActController act ;
    private HeaderVerifyController verify ;

    protected Driver driver;

    HeaderPage(Driver driver) {
        this.driver = driver;
    }

    // it is private constructor to force getting page object via static method getHomePage
    private HeaderPage(Driver driver, HeaderActController act, HeaderVerifyController verify) {
        this.driver = driver;
        this.act = act;
        this.verify = verify;
        PageFactory.initElements(driver.getWebDriver(), this);
    }

    protected By versicherungenHlink = By.xpath("//a[@href='/versicherungen/' and @class='page-navigation-text icn-a-angle-right-outlined icn-shield-outlined']");

    protected By privathaftpflichtHlink = By.xpath("//a[@href='/privathaftpflicht/' and @class='page-navigation-link']");

    protected By familistandDdl = By.xpath("//select[@name='situationGroup']");

    protected By ageTxtBox = By.name("age");

    protected By submitBtn =By.xpath("//button[@class='page-button' and @type='submit']");


    public static HeaderPage getHeader(Driver driver) {
        return new HeaderPage(driver, new HeaderActController(driver), new HeaderVerifyController(driver)).get();
    }

    public HeaderActController act() {
        return act;
    }

    public HeaderVerifyController verify() {
        return verify;
    }


    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        Assertions.assertAll(
                () -> driver.findElement(versicherungenHlink).verifyDisplayed(true)
        );
        log.info("Header Loaded Successfully");
    }
}
