package com.qa.verivox.core.driverUtils;

import com.microsoft.playwright.Playwright;
import com.qa.verivox.core.conf.BrowserConfig;
import com.qa.verivox.core.driverUtils.web.ChromeBrowser;
import com.qa.verivox.core.driverUtils.web.FirefoxBrowser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


@Slf4j
public enum PlaywrightFactory {

    FIREFOX("firefox", "ff") {
        @Override
        public MainBrowser getBrowser(BrowserConfig config) {
            return new FirefoxBrowser(config);
        }
    },
    CHROME("chrome", "ch") {
        @Override
        public MainBrowser getBrowser(BrowserConfig config) {
            return new ChromeBrowser(config);
        }
    };

    private final String[] values;

    public MainBrowser getBrowser(BrowserConfig config) {
        log.error("{} browser not supported yet!", this.name().toLowerCase());
        throw new UnsupportedOperationException(this.name());
    }

    PlaywrightFactory(String... v) {
        values = v;
    }

    public static PlaywrightFactory fromValue(String name) {
        if (StringUtils.isBlank(name)) {
            log.error("Browser is not defined in system variables, {} ", Arrays.toString(PlaywrightFactory.values()));
            throw new IllegalArgumentException();
        }

        for (PlaywrightFactory playwright : PlaywrightFactory.values()) {
            for (String value : playwright.values) {
                if (value.equalsIgnoreCase(name)) {
                    return playwright;
                }
            }
        }

        log.error("{} is invalid browse name, {}", name, Arrays.toString(PlaywrightFactory.values()));
        throw new IllegalArgumentException(name);
    }
}
