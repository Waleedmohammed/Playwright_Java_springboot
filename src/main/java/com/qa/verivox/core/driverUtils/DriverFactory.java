package com.qa.verivox.core.driverUtils;

import com.qa.verivox.core.conf.DriverConfig;
import com.qa.verivox.core.driverUtils.web.WebChromeDriver;
import com.qa.verivox.core.driverUtils.web.WebFirefoxDriver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


@Slf4j
public enum DriverFactory {

    FIREFOX("firefox", "ff") {
        @Override
        public Driver getDriver(DriverConfig config) {
            return new WebFirefoxDriver(config);
        }
    },
    CHROME("chrome", "ch") {
        @Override
        public Driver getDriver(DriverConfig config) {
            return new WebChromeDriver(config);
        }
    };

    private final String[] values;

    public Driver getDriver(DriverConfig config) {
        log.error("{} browser not supported yet!", this.name().toLowerCase());
        throw new UnsupportedOperationException(this.name());
    }

    DriverFactory(String... v) {
        values = v;
    }

    public static DriverFactory fromValue(String name) {
        if (StringUtils.isBlank(name)) {
            log.error("Browser is not defined in system variables, {} ", Arrays.toString(DriverFactory.values()));
            throw new IllegalArgumentException();
        }

        for (DriverFactory driver : DriverFactory.values()) {
            for (String value : driver.values) {
                if (value.equalsIgnoreCase(name)) {
                    return driver;
                }
            }
        }

        log.error("{} is invalid browse name, {}", name, Arrays.toString(DriverFactory.values()));
        throw new IllegalArgumentException(name);
    }
}
