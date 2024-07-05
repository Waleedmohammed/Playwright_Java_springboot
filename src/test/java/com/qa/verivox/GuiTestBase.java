package com.qa.verivox;

import com.qa.verivox.core.conf.AppProperties;
import com.qa.verivox.core.conf.DriverConfig;
import com.qa.verivox.core.driverUtils.Driver;
import com.qa.verivox.core.driverUtils.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class GuiTestBase extends AbstractTestNGSpringContextTests {

    @Autowired
    protected DriverConfig driverConfig;

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    DriverManager driverManager;

    public Driver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = driverManager.getDriver();
        driver.start();
        driver.open(appProperties.getAppUrl());
        driver.maximize();
    }

    // take screenshot when test case fail and add it in the Screenshot folder
    @AfterMethod(alwaysRun = true)
    public void screenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Failed!");
            System.out.println("Taking Screenshot....");
            driver.captureScreenshot(result.getName());
        }
        driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void afterAll() {
        driverManager.quitAllDrivers();
        driver.stopDriverService();
    }
}
