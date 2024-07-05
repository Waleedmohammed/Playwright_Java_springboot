package com.qa.verivox.pages.Home;

import com.qa.verivox.core.driverUtils.Driver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

@Slf4j
public class MainPage extends LoadableComponent<MainPage> {

    private MainActController act;

    private MainVerifyController verify;

    protected Driver driver;

    protected By searchTxtBox = By.xpath("//textarea[@class='gLFyf']");

    protected By acceptCookies = By.xpath("//div[@class='QS5gu sy4vM']");

    protected By searchBtn = By.xpath("//input[@name='btnK']");

    protected By logo = By.xpath("//*[@id=\"logo\"]/img");

    MainPage(Driver driver) {
        this.driver = driver;
    }

    // it is private constructor to force getting page object via static method getHomePage
    private MainPage(Driver driver, MainActController act, MainVerifyController verify) {
        this.driver = driver;
        this.act = act;
        this.verify = verify;
        PageFactory.initElements(driver.getWebDriver(), this);
    }

    public static MainPage getHomePage(Driver driver) {
        return new MainPage(driver, new MainActController(driver), new MainVerifyController(driver));
    }

    public MainActController act() {
        return act;
    }

    public MainVerifyController verify() {
        return verify;
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        Assertions.assertAll(
                () -> driver.findElement(By.xpath("//textarea[@class='gLFyf']")).verifyDisplayed(true)
        );
        log.info("Main Page Loaded Successfully");
    }
}
