package com.qa.verivox.pages.privathaftpflicht;

import com.qa.verivox.core.driverUtils.Driver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

@Slf4j
public class PrivathaftpflichtPage extends LoadableComponent<PrivathaftpflichtPage> {
    private PrivathaftpflichtActController act;
    private PrivathaftpflichtVerifyController verify;

    protected Driver driver;

    PrivathaftpflichtPage(Driver driver) {
        this.driver = driver;
    }

    // it is private constructor to force getting page object via static method getHomePage
    private PrivathaftpflichtPage(Driver driver, PrivathaftpflichtActController act, PrivathaftpflichtVerifyController verify) {
        this.driver = driver;
        this.act = act;
        this.verify = verify;
        PageFactory.initElements(driver.getWebDriver(), this);
    }

    protected By familistandDdl = By.xpath("//select[@name='situationGroup']");

    protected By ageTxtBox = By.name("age");

    protected By submitBtn = By.xpath("//button[@class='page-button' and @type='submit']");

    protected By familistandFormDdl = By.xpath("//*[@id='prestep_insureGroup']");

    protected By geburtsdatumTxtBox = By.xpath("//*[@class='ds-input__container']/input[@type='text']");

    protected By postalCodeTxtBox = By.xpath("//*[@id='prestep_postcode-input' and @type='tel']");

    protected By vergleichenBtn = By.xpath(".//button[contains(text(),'Jetzt vergleichen')]");




    public static PrivathaftpflichtPage getprivathaftpflichtPage(Driver driver) {
        return new PrivathaftpflichtPage(driver, new PrivathaftpflichtActController(driver), new PrivathaftpflichtVerifyController(driver));
    }

    public PrivathaftpflichtActController act() {
        return act;
    }

    public PrivathaftpflichtVerifyController verify() {
        return verify;
    }


    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        Assertions.assertAll(
                () -> driver.findElement(familistandDdl).verifyDisplayed(true)
        );
        log.info("Privathaftpflicht Page Loaded Successfully");
    }
}
