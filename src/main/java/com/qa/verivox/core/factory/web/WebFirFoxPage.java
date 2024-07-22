package com.qa.verivox.core.factory.web;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.qa.verivox.core.conf.BrowserConfig;
import com.qa.verivox.core.factory.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebFirFoxPage extends BasePage {

    public WebFirFoxPage(BrowserConfig config) {
        super(config);
    }

    @Override
    protected Page init() {
        tlPlaywright.set(Playwright.create());
        tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(browserConfig.isHeadless())));

        tlBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions()
                .setAcceptDownloads(true)
                .setStrictSelectors(false)
                .setJavaScriptEnabled(true)
                .setLocale("")
                .setViewportSize(1920, 1080)
                .setTimezoneId("")));
        tlPage.set(getBrowserContext().newPage());
        return getPage();
    }

}
