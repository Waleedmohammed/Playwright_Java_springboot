package com.qa.verivox.core.driverUtils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

@Slf4j
public class Element {

    private WebElement element;
    private Driver driver;

    public Element(WebElement element, Driver driver) {
        this.element = element;
        this.driver = driver;
    }

    public void verifyDisplayed(boolean is) {
        Assertions.assertEquals(is, element.isDisplayed());
    }

    public void click() throws Exception {
        try {
            driver.waitForElementToBeClickable(element).click();
            log.info(element + " is succesfully clicked");
        } catch (Exception e) {
            log.error("Cannot click " + element);
            e.printStackTrace();
            throw new Exception("Cannot click " + element);
        }
    }

    public void enterText(String value) throws Exception {
        try {
            driver.waitForElementToBeClickable(element).clear();
            element.sendKeys(value);
            log.info("Entered value: " + value + " on " + element);
        } catch (Exception e) {
            log.error("Cannot enter value in : " + value + " on " + element);
            throw new Exception("Cannot enter value in : " + element);
        }
    }

    public String getText() {
        try {
            String text = driver.waitForElementToBeClickable(element).getText();
            log.info(element + " found with gettext String: " + text);
            return text;
        } catch (Exception e) {
            log.error(element + " not found with gettext String");
            return null;
        }
    }

    public void moveToElement() throws Exception {
        try {
            driver.getActions().moveToElement(driver.waitForElementToBeClickable(element)).build().perform();
            log.info("Cursor moved to element: " + element);
        } catch (Exception e) {
            log.error("Cursor not moved to element: " + element);
            throw new Exception("Cannot move cursor " + element);
        }
    }


    public Driver getDriver() {
        return driver;
    }

    public boolean isDisplayed() {
        if (element.isDisplayed())
            log.info(element + " is displayed");
        else
            log.info(element + " is not displayed");
        return element.isDisplayed();
    }

    public boolean isElementClickable() {
        try {
            driver.waitForElementToBeClickable(element);
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

}
