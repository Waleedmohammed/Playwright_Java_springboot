package com.qa.verivox.pages.privathaftpflicht;

import com.qa.verivox.core.driverUtils.Driver;
import com.qa.verivox.pages.common.HeaderPage;
import org.junit.jupiter.api.Assertions;

public class PrivathaftpflichtVerifyController extends PrivathaftpflichtPage {
    PrivathaftpflichtVerifyController(Driver driver) {
        super(driver);
    }

    public PrivathaftpflichtVerifyController familiestandDdlInFormIsDisplayed() throws Exception {
        Assertions.assertTrue(driver.findElement(familistandFormDdl).isDisplayed());
        return this;
    }

    public PrivathaftpflichtVerifyController geburtsdatumIsDisplayed() throws Exception {
        Assertions.assertTrue(driver.findElement(geburtsdatumTxtBox).isDisplayed());
        return this;
    }

    public PrivathaftpflichtVerifyController tarrifsShownSuccessfully() throws Exception {
        Assertions.assertTrue(driver.findElements(productContainer).size() >= 5, "It is expected to have at least 5 products ... , Something Wrong in search functionality");
        return this;
    }
}
