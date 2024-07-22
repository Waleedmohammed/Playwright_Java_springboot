package com.qa.verivox.core.factory;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.qa.verivox.core.conf.BrowserConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

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

    public void navigate(String url) {
        page.navigate(url);
        log.info("opened {}", url);
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
            boolean visible = page.isVisible(locator, new Page.IsVisibleOptions()
                    .setTimeout(browserConfig.getExplicitlyWait()));
            log.info("Element located by " + locator + " is visible");
            return visible;
        } catch (Exception e) {
            log.error("Element located by " + locator + " is not visible");
            e.printStackTrace();
            throw new Exception("Element located by " + locator + " is not visible");
        }
    }

    public boolean isElementEnabled(String locator) throws Exception {
        try {
            boolean enabled = page.isEnabled(locator, new Page.IsEnabledOptions()
                    .setTimeout(browserConfig.getExplicitlyWait()));
            log.info("Element located by " + locator + " is enabled");
            return enabled;
        } catch (Exception e) {
            log.error("Element located by " + locator + " is not enabled");
            e.printStackTrace();
            throw new Exception("Element located by " + locator + " is not enabled");
        }
    }

    public boolean isElementChecked(String locator) throws Exception {
        try {
            boolean checked = page.isChecked(locator, new Page.IsCheckedOptions()
                    .setTimeout(browserConfig.getExplicitlyWait()));
            log.info("Element located by " + locator + " is checked");
            return checked;
        } catch (Exception e) {
            log.error("Element located by " + locator + " is not checked");
            e.printStackTrace();
            throw new Exception("Element located by " + locator + " is not checked");
        }
    }


    public void selectFromDDL(String locator, String value) throws Exception {
        try {
            page.selectOption(locator, value);
            log.info(value + " has been selected from DDL located by " + locator);
        } catch (Exception e) {
            log.error(value + " can not be selected from DDL located by " + locator);
            e.printStackTrace();
            throw new Exception(value + " can not be selected from DDL located by " + locator);
        }
    }

    public String getText(String locator) throws Exception {
        try {
            String text = page.innerText(locator);
            log.info(text + " retrieved from Element located by " + locator);
            return text;
        } catch (Exception e) {
            log.error("can not be get Text from Element located by " + locator);
            e.printStackTrace();
            throw new Exception("can not be get Text from Element located by" + locator);
        }
    }

    public void check(String locator) throws Exception {
        try {
            page.check(locator);
            log.info("Checkbox located by {} checked", locator);
        } catch (Exception e) {
            log.info("Checkbox located by {} not checked", locator);
            e.printStackTrace();
            throw new Exception("Checkbox located by " + locator + " not checked");
        }
    }

    public void unCheck(String locator) throws Exception {
        try {
            page.uncheck(locator);
            log.info("Checkbox located by {} unChecked", locator);
        } catch (Exception e) {
            log.info("Checkbox located by {} can not unChecked", locator);
            e.printStackTrace();
            throw new Exception("Checkbox located by " + locator + " can not unChecked");
        }
    }

    public void reload() {
        page.reload();
        log.info("Page reloaded .... ");
    }

    public void goBack() {
        page.goBack();
        log.info("Page goBack .... ");
    }

    public void goForward() {
        page.goForward();
        log.info("Page goForward .... ");
    }

    public void doubleClick(String locator) throws Exception {
        try {
            page.dblclick(locator);
            log.info("Double click on element located by {}", locator);
        } catch (Exception e) {
            log.info("Can not Double click on element located by {}", locator);
            e.printStackTrace();
            throw new Exception("Can not Double click on element located by " + locator);
        }
    }

    public void acceptAlert() {
        page.onDialog(Dialog::accept);
        log.info("Alert Accepted ....");
    }

    public void dismissAlert() {
        page.onDialog(Dialog::dismiss);
        log.info("Alert dismissed ....");
    }

    public void TypeTextInAlert(String text) {
        page.onDialog(dialog -> dialog.accept(text));
        log.info("Typed {} in alert and moving on ....", text);
    }

    public void download(String text) {
        page.onDownload(download -> download.saveAs(Paths.get(new File(browserConfig.getDownloadPath()).toURI())));
        log.info("Typed {} in alert and moving on ....", text);
    }

    public void waitForDownload(String downloadBtnLocator) {
        Download download = page.waitForDownload(() -> page.click(downloadBtnLocator));
        Path path = download.path();
        log.info("File downloaded to {}", path);
    }

}
