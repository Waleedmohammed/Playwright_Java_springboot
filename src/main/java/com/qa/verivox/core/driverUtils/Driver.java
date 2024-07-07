package com.qa.verivox.core.driverUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.qa.verivox.core.conf.DriverConfig;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public abstract class Driver {

    protected DriverConfig driverConfig;
    protected static DriverService service;
    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement elementToBePresent;

    private WebElement element;

    public Driver(DriverConfig driverConfig) {
        this.driverConfig = driverConfig;
    }

    public void start() {
        log.info("Starting driver session...");
        driver = init();
        WebDriverListener listener = new DriverEventListener();
        WebDriver webDriver = new EventFiringDecorator<>(listener).decorate(driver);

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(driverConfig.getImplicitlyWait()));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(driverConfig.getPageLoadTimeout()));

        log.info("driver initialized");
        if (driverConfig.isMaximize()) {
            maximize();
        }
        log.info("Deleting all the cookies");
        webDriver.manage().deleteAllCookies();

    }

    protected abstract WebDriver init();


    protected void startDriverService(DriverService.Builder builder) {
        if (service == null || !service.isRunning()) {
            service = builder
                    .usingAnyFreePort()
                    .build();
            try {
                getService().start();
            } catch (Exception e) {
                service = null;
                throw new RuntimeException(e);
            }
            log.info("started DriverService started");
        }
    }

    public void stopDriverService() {
        if (service != null && service.isRunning()) {
            quit();
            service.stop();
            service = null;
            log.info("DriverService stopped");
        }
    }

    public DriverService getService() {
        return service;
    }

    public void open(String url) {
        driver.get(url);
        log.info("{} opened", url);
    }

    public String getTitle() {
        String title = driver.getTitle();
        log.info("Title is {}", title);
        return title;
    }

    public void maximize() {
        driver.manage().window().maximize();
        log.info("Browser maximized");
    }

    public void minimize() {
        driver.manage().window().minimize();
        log.info("Browser minimized");
    }

    public Actions doAction(Keys keys) throws InterruptedException {
        Actions action = new Actions(driver);
        return action.sendKeys(keys);
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public Actions getActions() {
        return new Actions(driver);
    }

    public Object executeScript(String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script, args);
    }

    public Element findElement(By by, Duration timeout, Duration every) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(every)
                .ignoring(NoSuchElementException.class);

        return new Element(wait.until(driver -> driver.findElement(by)), this);
    }

    public Element findElement(By by) {
        return new Element(driver.findElement(by), this);
    }


    public List<Element> findElements(By by) {

        List<WebElement> elements = driver.findElements(by);

        log.info("There are {} elements found with xpth {}", elements.size(), by);

        return elements.stream()
                .map(element -> new Element(element, this))
                .collect(Collectors.toList());
    }


    public Element findElement(ExpectedCondition<WebElement> expected) {
        return findElement(expected, Duration.ofSeconds(5));
    }

    public Element findElement(ExpectedCondition<WebElement> expected, Duration timeout) {
        return new Element(new WebDriverWait(driver, timeout)
                .until(expected), this);
    }

    public void waitForLoading(Duration timeout) throws InterruptedException {
        Thread.sleep(5000);
        ExpectedCondition<Boolean> expectation = webDriver ->
        {
            assert webDriver != null;
            return (Boolean) executeScript("var page=(document.readyState == 'complete');" +
                    " var ajax; try{ajax = (jQuery.active==0);}catch(e){ajax= true} return (page && ajax)");
        };
        new WebDriverWait(driver, timeout).until(expectation);
    }

    public void waitForPresenceOfElementLocated(By locator) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(driverConfig.getExplicitlyWait()));
        elementToBePresent = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    public WebElement waitForElementToBeClickable(WebElement element) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(driverConfig.getExplicitlyWait()));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForVisibilityOfElementLocated(By locator) throws InterruptedException {
        Thread.sleep(3000);
        wait = new WebDriverWait(driver, Duration.ofSeconds(driverConfig.getExplicitlyWait()));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void implicitWait(int timeoutInSeconds) {
        driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
    }

    public void verifyPageUrl(String expected) {
        MatcherAssert.assertThat(driver.getCurrentUrl(), Matchers.containsString(expected));
    }


    public void captureScreenshot(String screenshotName) {
        Path dest = Paths.get("./Screenshots", screenshotName + "_" + driverConfig.getName() + ".png");
        try {
            Files.createDirectories(dest.getParent());
            FileOutputStream out = new FileOutputStream(dest.toString());
            out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            log.info("Screenshot taken and stored as : '" + dest + "'");
            out.close();
        } catch (IOException e) {
            System.out.println("Excpetion while taking screenshot" + e.getMessage());
        }
    }

    public Boolean isElementExist(By locator) {
        boolean isElementExist = false;
        if (!driver.findElements(locator).isEmpty()) {
            isElementExist = true;
        }
        return isElementExist;
    }


    public void quit() {
        if (driver != null) {
            driver.quit();
            log.info("Driver quit");
        }
    }


    public String getHtmlTableCellTxt(By locator, int rowNum, int colNum) {

        Table<Integer, Integer, String> tableObj = HashBasedTable.create();

        WebElement table = driver.findElement(locator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        int rowIndex = 0;
        int colIndex = 0;

        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            for (WebElement col : cols) {
                tableObj.put(rowIndex, colIndex, col.getText());
            }
            rowIndex++;
            colIndex = 0;
        }
        return tableObj.get(rowNum, colNum);
    }

    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        log.info("Accepting Alert ****");

        alert.accept();
    }

    public void dismissAlert() {
        Alert alert = driver.switchTo().alert();
        log.info("Dismissing Alert ****");

        alert.dismiss();
    }

    public void getAlertText() {
        Alert alert = driver.switchTo().alert();
        log.info("Getting Alert Text ****");

        alert.getText();
    }

    public void typeInAlertTxt(String text) {
        Alert alert = driver.switchTo().alert();
        log.info("Typing {} in Alert ******", text);

        alert.sendKeys(text);
    }

    public void uploadFile(File file, By uploaderLocator) {

        WebElement uploadText = driver.findElement(uploaderLocator);

        uploadText.sendKeys(file.getAbsolutePath());

        log.info("Uploading file {} ******", file.getAbsolutePath());
    }

    public SessionStorage getSessionStorage() {
        WebStorage webStorage = (WebStorage) driver;
        SessionStorage sessionStorage = webStorage.getSessionStorage();
        return sessionStorage;
    }


//    public LogEntries assertNoErrorInLogs() {
//        LogEntries logs =  driver.manage().logs().get(LogType.BROWSER);
//        logs.forEach(this::checkNoErrors);
//        return logs;
//    }
//
//    private void checkNoErrors(LogEntry logs) {
//        logs.getLevel().getName();
//    }


}
