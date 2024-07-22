package com.qa.verivox.pages.privathaftpflicht;

import com.qa.verivox.core.driverUtils.Driver;
import lombok.extern.slf4j.Slf4j;


import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class PrivathaftpflichtVerifyController extends PrivathaftpflichtPage {


    PrivathaftpflichtVerifyController(Driver driver) {
        super(driver);
    }

    public PrivathaftpflichtVerifyController familiestandDdlInFormIsDisplayed() throws InterruptedException {
        assertTrue(driver.findElement(familistandFormDdl).isDisplayed());
        return this;
    }

    public PrivathaftpflichtVerifyController geburtsdatumIsDisplayed() {
        assertTrue(driver.findElement(geburtsdatumTxtBox).isDisplayed());
        return this;
    }

}
