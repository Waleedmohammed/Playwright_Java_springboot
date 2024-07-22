package com.qa.verivox.core.factory;

import com.qa.verivox.core.conf.BrowserConfig;
import com.qa.verivox.core.factory.web.WebChromePage;
import com.qa.verivox.core.factory.web.WebFirFoxPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


@Slf4j
public enum PageFactory {

    FIREFOX("firefox", "ff") {
        @Override
        public BasePage getPage(BrowserConfig config) {
            return new WebFirFoxPage(config);
        }
    },
    CHROME("chrome", "ch") {
        @Override
        public BasePage getPage(BrowserConfig config) {
            return new WebChromePage(config);
        }
    };

    private final String[] values;

    public BasePage getPage(BrowserConfig config) {
        log.error("{} browser not supported yet!", this.name().toLowerCase());
        throw new UnsupportedOperationException(this.name());
    }

    PageFactory(String... v) {
        values = v;
    }

    public static PageFactory fromValue(String name) {
        if (StringUtils.isBlank(name)) {
            log.error("Browser is not defined in system variables, {} ", Arrays.toString(PageFactory.values()));
            throw new IllegalArgumentException();
        }

        for (PageFactory page : PageFactory.values()) {
            for (String value : page.values) {
                if (value.equalsIgnoreCase(name)) {
                    return page;
                }
            }
        }

        log.error("{} is invalid browse name, {}", name, Arrays.toString(PageFactory.values()));
        throw new IllegalArgumentException(name);
    }
}
