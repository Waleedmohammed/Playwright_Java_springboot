package com.qa.verivox.core.factory.web;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.qa.verivox.core.conf.BrowserConfig;
import com.qa.verivox.core.factory.BasePage;
import com.qa.verivox.utils.TestHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebChromePage extends BasePage {

    public WebChromePage(BrowserConfig config) {
        super(config);
    }

    @Override
    protected Page init(Browser.NewContextOptions context) {

        tlPlaywright.set(Playwright.create());
        tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(browserConfig.isHeadless())));
        if (context != null) {
            tlBrowserContext.set(getBrowser().newContext(context));
        }else {
            tlBrowserContext.set(getBrowser().newContext());
        }
        tlPage.set(getBrowserContext().newPage());
        return getPage();
    }

}
