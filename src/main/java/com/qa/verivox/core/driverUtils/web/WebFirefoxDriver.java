package com.qa.verivox.core.driverUtils.web;


import com.qa.verivox.core.conf.DriverConfig;
import com.qa.verivox.core.driverUtils.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

@Slf4j
public class WebFirefoxDriver extends Driver {

    public WebFirefoxDriver(DriverConfig driverConfig) {
        super(driverConfig);
    }

    @Override
    protected WebDriver init() {

        String firFoxPath = driverConfig.getFirFoxPath();

        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        if (driverConfig.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.fromString(driverConfig.getPageLoadingStrategy()));
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        options.setProfile(profile);


        WebDriverManager manager = WebDriverManager.firefoxdriver();

        if (!StringUtils.isBlank(firFoxPath)) {
            manager.browserVersionDetectionCommand(firFoxPath + " --version");
            options.setBinary(firFoxPath);
        }

        manager.setup();

        if (driverConfig.isDriverService()) {
            startDriverService(new GeckoDriverService.Builder());
            return new RemoteWebDriver(service.getUrl(), options);
        } else {
            return new FirefoxDriver(options);
        }
    }

}
