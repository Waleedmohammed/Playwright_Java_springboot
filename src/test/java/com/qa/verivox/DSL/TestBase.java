package com.qa.verivox.DSL;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.qa.verivox.core.conf.AppProperties;
import com.qa.verivox.core.conf.BrowserConfig;

import com.qa.verivox.core.factory.PageManager;

import com.qa.verivox.core.factory.BasePage;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestBase extends AbstractTestNGSpringContextTests {

    @Autowired
    protected BrowserConfig browserConfig;

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    protected PageManager pageManager;
    BasePage basePage;
    Browser.NewContextOptions contextOptions;
    public static ExtentTest logger;
    public static ExtentReports report;

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
        // Because we are using Toolkit.getDefaultToolkit().getScreenSize() which is not supporting headless mode
        if (!browserConfig.isHeadless()) {
            System.setProperty("java.awt.headless", "false");
        }
        contextOptions = new Browser.NewContextOptions()
                .setAcceptDownloads(true)
                .setViewportSize((int)getCurrentScreenDimension().getWidth(),(int)getCurrentScreenDimension().getHeight());
        logger = report.createTest(method.getName());
        basePage = pageManager.getPage();
        basePage.start(contextOptions);
        basePage.navigate(browserConfig.getAppUrl());
    }

    // take screenshot when test case fail and add it in the Screenshot folder
    @AfterMethod(alwaysRun = true)
    public void screenshotOnFailure(ITestResult result, Method method) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Failed!");
            System.out.println("Taking Screenshot....");
            basePage.takeScreenshot();
        }
        // log the test method's execution result. if it fails, log the assertion error
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, method.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, method.getName());
        }
        basePage.quit();
    }

    @AfterTest
    public void endReport() {
        report.flush();
        basePage.quit();
    }

    @AfterSuite
    public void afterAll() {
        basePage.quit();
    }

    private static Dimension getCurrentScreenDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
