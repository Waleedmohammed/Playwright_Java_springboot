package com.qa.verivox.cucumber.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.platform.suite.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/* For using Junit Runner  */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, " + // Generate Human readable test results in logs
        "html:target/cucumber-report.html, " + // Generate HTML Report
        "json:target/cucumber-report.json, " + // Generate XML Report
        "junit:target/cucumber-reports.xml, " + // XML report to be used for CI tools (eg: Jenkins
        "timeline:target/cucumber-report,  " + // Identifiy parallel execution patterns
        "usage:target/usage.json, " + // Statistic about step definitions (number of time each step executed)
        "rerun:target/rerun.txt") // text file includes the failed scenarios (input to rerun only failed test scenarios
@ConfigurationParameter(key = "cucumber.publish.enabled", value = "true")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.qa.verivox")

/* For using TestNG Runner  */
/*@CucumberOptions(tags = "", features = {"src/test/resources/features/DSLCalculator.feature"}, glue = {"com.qa.verivox"},
        plugin = {"pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json",
                "junit:target/cucumber-reports.xml",
                "timeline:target/cucumber-report",
                "usage:target/usage.json",
                "rerun:target/rerun.txt"},
        publish = true)*/
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
