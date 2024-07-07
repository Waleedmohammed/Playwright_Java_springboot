package com.qa.verivox.pages.common;

import com.qa.verivox.core.driverUtils.Driver;
import com.qa.verivox.pages.Home.HomeActController;

public class HeaderActController extends HeaderPage {

    HeaderActController(Driver driver) {
        super(driver);
    }

    public HeaderActController mouseHoverVersicherungTab() throws Exception {
        driver.findElement(versicherungenHlink).moveToElement();
        return this;
    }

    public HeaderActController goToPrivathaftpflichtPage() throws Exception {
        driver.findElement(privathaftpflichtHlink).click();
        return this;
    }

    public HeaderActController waitForFamilistandDropDownList() throws Exception {
        driver.waitForPresenceOfElementLocated(familistandDdl);
        return this;
    }

    public HeaderActController selectFamilistand(String familistandValue) throws Exception {
        driver.findElement(familistandDdl).selectValueFromDDL(familistandValue);
        return this;
    }

    public HeaderActController enterAge(int age) throws Exception {
        driver.findElement(ageTxtBox).enterText(Integer.toString(age));
        return this;
    }

    public HeaderActController clickJetztVergleichenButton() throws Exception {
        driver.findElement(submitBtn).click();
        return this;
    }
}
