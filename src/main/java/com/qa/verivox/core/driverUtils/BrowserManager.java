package com.qa.verivox.core.driverUtils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.qa.verivox.core.conf.BrowserConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class PlaywrightManager {
    private List<MainBrowser> browsers = Collections.synchronizedList(new ArrayList<>());
    private BrowserConfig config;

    public PlaywrightManager(BrowserConfig config) {
        this.config = config;
    }

    public MainBrowser getBrowser() {
        return getBrowser(config.getName());
    }

    public MainBrowser getBrowser(String name) {
        MainBrowser driver = PlaywrightFactory.fromValue(name).getBrowser(config);
        browsers.add(driver);
        log.info("I have browsers " + driver);
        return driver;
    }

    public void quitAllBrowsers() {
        browsers.forEach((MainBrowser::close));
        browsers.clear();
    }
}


