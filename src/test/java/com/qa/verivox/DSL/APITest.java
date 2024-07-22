package com.qa.verivox.DSL;

import com.github.dockerjava.transport.DockerHttpClient;
import com.microsoft.playwright.Response;
import com.qa.verivox.core.factory.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class APITest extends TestBase {

    Response response;

    @BeforeMethod
    @Override
    public void setUp(Method method) {
        logger = report.createTest(method.getName());
        basePage = pageManager.getPage();
        basePage.start();
        response = basePage.navigate(browserConfig.getAppUrl());
    }

    @Test
    public void TestResponse() throws Exception {
        log.info("API URL {}", response.url());
        log.info("Response status {}", response.status());
        log.info("Response is {}", response.ok());
        log.info("Response Headers are {}", response.headers());
        log.info("Response body is {}", response.text());
    }

    @Test
    public void TestRequest() throws Exception {
        log.info("API URL {}", response.request().url());
        log.info("Request Body {}", response.request().postData());
        log.info("Request Headers are {}", response.request().headers());
        log.info("Request method is {}", response.request().method());
    }

    @Test
    public void TestHTTPTraffic() throws Exception {
        List<Integer> statuses = basePage.getAllResponseStatus();
        basePage.printAllRequestResponses();
        basePage.navigate(browserConfig.getAppUrl());
        log.info("Status codes are {}",statuses);
        Assertions.assertTrue(basePage.getAllResponseStatus().stream().anyMatch(i -> i < 200 || i >= 300));

    }
}
