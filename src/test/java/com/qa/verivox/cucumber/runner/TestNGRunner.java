package com.qa.verivox.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = {"src/test/resources/features/DSLCalculator.feature",
        "src/test/resources/features/TarrifSearchResults.feature"},
        glue = {"com.qa.verivox"},
        plugin = {"pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json",
                "junit:target/cucumber-reports.xml",
                "timeline:target/cucumber-report",
                "usage:target/usage.json",
                "rerun:target/rerun.txt"},
        publish = true)
public class TestNGRunner extends AbstractTestNGCucumberTests {

}
