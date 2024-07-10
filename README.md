# Verivox
This repo is for solving the tech task as part of the interview process of Verivox . A framework designed to test two main features (DSL Calculator and Tarrif Search page).

* Needed Setup to run the tests:
    * JAVA
    * MAVEN
    * Junit
    * TestNG

### Installation

1. Download and install Maven
2. Download Intelij with Java JDK 17
3. Install TestNG
4. Clone this Repo

## Features

* This Project built with Spring boot Framework
* Used Selenium Webdriver with Page Object Model Design used 
* WebDriver manager : No need to download the driver manually , just configure your test browser and its related driver will be downloaded automatically
* Using Cucumber testing framework to apply the design of BDD based on Gherkin language
* Visualized Reporting - *Using ExtentReport and Cucumber Reports
* Report Should be generated automatically under "./test-output/ExtentReport.html"
* In addition to Cucumber framework , I intended to apply concept of Fluent Interface with Method chaining to build tests with Syntactic Sugar Language to achieve Domain Specific Language(DSL)
* Tests with DSL (Domain Specific Language) located at "src/test/java/com/qa/verivox/DSL"
* Test with Cucumber Framework located at "src/test/resources/features"

## Framework Structure
1. src/main/java/com/qa/verivox/core
   * here located all Driver / WebElement Wrappers + Properties classes

2. src/main/java/com/qa/verivox/pages
   * here located all Page Objects with main three classes for each page
     *  PageClass : contain each page elements locators
     *  PageAct : Contain all action methods needed for each page
     *  PageVerify : Contain all Verification methods needed to apply verification in each page
3. src/main/java/com/qa/verivox/utils
   * here small package has Test helper class

4. src/main/resources
   * here located application/Driver properties 
     *  application.properties : Property file contains application related configs 
     *  application-chrome-test.properties : Property file to apply all needed configs for chrome driver
     *  application-firefox-test.properties : Property file to apply all needed configs for firefox driver

5. src/test/java/com/qa/verivox/cucumber
   * here all cucumber tests implementation (TestRunner+stepDefinitions)

6. src/test/java/com/qa/verivox/DSL
   * If you would like to enjoy Syntactic Sugar World , enjoy the same tests implementation here :) 

7. src/test/resources/features
   * Cucumber features files are located here


## How To Run Tests
1. To run Cucumber test suite from command line just run ``` mvn clean test ``` from your terminal/cmd from root directory
   * The HTML report is generated automatically in the path target/cucumber-report.html
     ![Report sample](https://github.com/Waleedmohammed/QAChallenge_Verivox/blob/master/CucumberReport.png)
   
   * Logs also printed in terminal/cmd with a human readable language

2. If you would like to run non cucumber Tests , 
   * Enable related Plugin in pom file , it is already there and disabled at the moment
   * run ``` mvn clean test -Pdsl-tests ```
   * The HTML report is generated automatically in the path ./Report.html
     ![Report sample](https://github.com/Waleedmohammed/QAChallenge_Verivox/blob/master/ExtentReport.png)
   
   * Logs also printed in terminal/cmd with a human readable language
   ![Log sample](https://github.com/Waleedmohammed/QAChallenge_Verivox/blob/master/command.log)



