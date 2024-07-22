package com.qa.verivox.core.factory;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.qa.verivox.core.conf.BrowserConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Slf4j
@Component
public abstract class BasePage {

    protected static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    protected static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    protected static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    protected static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    protected BrowserConfig browserConfig;
    private Page page;


    public BasePage(BrowserConfig browserConfig) {
        this.browserConfig = browserConfig;
    }


    public void start() {

        String browserName = browserConfig.getName();
        log.info("Initialising browser : {} ", browserName);

        log.info("Starting page session...");
        page = init();
        page.setDefaultTimeout(browserConfig.getExplicitlyWait());
    }

    protected abstract Page init();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public void quit() {
        if (page != null) {
            page.context().close();
            log.info("Page context closed");
        }
    }

    public String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/Screenshots/" + System.currentTimeMillis() + ".png";
        //getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));

        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);

        return base64Path;
    }

    public String getTitle() {
        String title = page.title();
        log.info("Title is {}", title);
        return title;
    }

    public void clickOn(String locator) throws Exception {
        try {
            page.click(locator);
            log.info("Element located by " + locator + " is succesfully clicked");
        } catch (Exception e) {
            log.error("Cannot click element located by " + locator);
            e.printStackTrace();
            throw new Exception("Cannot click element located by" + locator);
        }
    }

    public void typeIn(String Locator, String text) throws Exception {
        try {
            page.fill(Locator, text);
            log.info("Entered value: " + text + " on element located by " + Locator);
        } catch (Exception e) {
            log.error("Cannot enter value in : " + text + " on element located by " + Locator);
            throw new Exception("Cannot enter value in element located by : " + Locator);
        }
    }

    public int findElementsCountLocatedBy(String locator) {
        page.locator(locator)
                .waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(browserConfig.getExplicitlyWait()));
        Integer obj = (Integer) page.evalOnSelectorAll(locator, "e=> e.length");
        log.info("There are {} elements found with xpth {}", obj, locator);
        return obj;
    }

    public boolean isElementVisible(String locator) throws Exception {
        try {
            page.locator(locator).isVisible(new Locator.IsVisibleOptions()
                    .setTimeout(browserConfig.getExplicitlyWait()));
            log.info("Element located by " + locator + " is visible");
            return true;
        } catch (Exception e) {
            log.error("Element located by " + locator + " is not visible");
            e.printStackTrace();
            throw new Exception("Element located by " + locator + " is not visible");
        }
    }
}
