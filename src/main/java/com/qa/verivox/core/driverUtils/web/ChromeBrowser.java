package com.qa.verivox.core.driverUtils.web;


import com.microsoft.playwright.BrowserContext;
import com.qa.verivox.core.conf.BrowserConfig;
import com.qa.verivox.core.driverUtils.Browser;
import com.qa.verivox.core.driverUtils.MainBrowser;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.logging.Level;


@Slf4j
public class ChromeBrowserContext extends MainBrowser {

    public ChromeBrowserContext(BrowserConfig config) {
        super(config);
    }

    @Override
    protected BrowserContext init() {


        browser.newContext()

        // options.setExperimentalOption() -- to use emulation mode
        if (driverConfig.isHeadless()) {
            options.addArguments("--headless=new");
        }
        if (driverConfig.isGetConsoleLog()) {
            LoggingPreferences logs = new LoggingPreferences();
            logs.enable(LogType.BROWSER, Level.ALL);
            options.setCapability(ChromeOptions.LOGGING_PREFS, logs);
        }

        WebDriverManager manager = WebDriverManager.chromedriver();

        if (!StringUtils.isBlank(chromePath)) {
            manager.browserVersionDetectionCommand(chromePath + " --version");
            options.setBinary(chromePath);
        }

        manager.setup();

        if (driverConfig.isDriverService()) {
            startDriverService(new ChromeDriverService.Builder());
            log.info("Service Started at " + service.getUrl());
            return new RemoteWebDriver(service.getUrl(), options);
        } else {
            return new ChromeDriver(options);
        }
    }

}
