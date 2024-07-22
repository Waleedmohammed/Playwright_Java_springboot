package com.qa.verivox.core.factory;

import com.qa.verivox.core.conf.BrowserConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class PageManager {
    private List<BasePage> drivers = Collections.synchronizedList(new ArrayList<>());
    private BrowserConfig config;

    public PageManager(BrowserConfig config) {
        this.config = config;
    }

    public BasePage getPage() {
        return getPage(config.getName());
    }

    public BasePage getPage(String name) {
        BasePage driver = PageFactory.fromValue(name).getPage(config);
        drivers.add(driver);
        return driver;
    }

    public void quitAllDrivers() {
        drivers.forEach((BasePage::quit));
        drivers.clear();
    }
}


