package com.qa.verivox.DSL;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.verivox.core.conf.AppProperties;
import com.qa.verivox.core.conf.DriverConfig;
import com.qa.verivox.core.driverUtils.Driver;
import com.qa.verivox.core.driverUtils.DriverManager;
import com.qa.verivox.pages.Home.HomePage;
import com.qa.verivox.pages.common.Header.HeaderPage;
import com.qa.verivox.pages.common.SearchResult.SearchPage;
import com.qa.verivox.pages.privathaftpflicht.PrivathaftpflichtPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestBase extends AbstractTestNGSpringContextTests {

    @Autowired
    protected DriverConfig driverConfig;

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    protected DriverManager driverManager;

    public Driver driver;

    private static ExtentTest logger;
    private static ExtentReports report;

    @BeforeTest
    public void initReport() {
        report = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("Report.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Reqres Automation Testing Report");
        report.attachReporter(spark);
    }

    @BeforeMethod
    public void setUp(Method method) {
        logger = report.createTest(method.getName());
        driver = driverManager.getDriver();
        driver.start();
        driver.open(appProperties.getAppUrl());
        driver.maximize();
    }

    // take screenshot when test case fail and add it in the Screenshot folder
    @AfterMethod(alwaysRun = true)
    public void screenshotOnFailure(ITestResult result, Method method) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Failed!");
            System.out.println("Taking Screenshot....");
            driver.captureScreenshot(result.getName());
        }
        // log the test method's execution result. if it fails, log the assertion error
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, method.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, method.getName());
        }
        driver.quit();
    }

    @AfterTest
    public void endReport() {
        report.flush();
    }

    @AfterSuite
    public void afterAll() {
        driverManager.quitAllDrivers();
        driver.stopDriverService();
    }
}
