package com.qa.verivox.pages.common.SearchResult;

import com.qa.verivox.core.driverUtils.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

@Slf4j
public class SearchPage extends LoadableComponent<SearchPage> {

    private SearchActController act;
    private SearchVerifyController verify;

    protected Driver driver;

    public int totalNumberOfResults;

    protected By productContainer = By.xpath("//div[@class='product-container']");

    protected By totalNumberResultLbl = By.xpath("//filtered-products-hint/span");

    protected By initialProductList = By.xpath("//div[@class='product-list']/product");

    protected By loadMoreBtn = By.xpath("//a[@class='button load-more-button']");

    protected By productDetailsList = By.xpath("//ul[@class='navigation']/li");


    SearchPage(Driver driver) {
        this.driver = driver;
    }

    // it is private constructor to force getting page object via static method getHomePage
    private SearchPage(Driver driver, SearchActController act, SearchVerifyController verify) {
        this.driver = driver;
        this.act = act;
        this.verify = verify;
        PageFactory.initElements(driver.getWebDriver(), this);
    }

    public static SearchPage getSearchPage(Driver driver) {
        return new SearchPage(driver, new SearchActController(driver), new SearchVerifyController(driver));
    }

    public SearchActController act() {
        return act;
    }

    public SearchVerifyController verify() {
        return verify;
    }


    protected int getTotalNumberOfResults() {
        totalNumberOfResults = Integer.parseInt(driver.findElement(totalNumberResultLbl).getText().split("[A-Z]")[0].trim());
        log.info("Total number of results for given search criteria are:{}", totalNumberOfResults);
        return totalNumberOfResults;
    }

    protected By getProductPriceLbl (int productNumber){
        return By.xpath("(.//div[contains(@class,'product-group product-group-action')])["+ productNumber +"]//preceding-sibling::div[@class='price']");
    }

    protected By getOnlineAngeboteBtn (int productNumber){
        return By.xpath("(.//button[@class='button secondary button-toggle'])["+ productNumber +"]/following-sibling::button");
    }

    protected By getProductDetailsBtn (int productNumber){
        return By.xpath("(.//button[@class='button secondary button-toggle'])["+ productNumber +"]");
    }



    protected void clickLoadMoreButtonIfApplicable(int searchResultCount) throws Exception {

        int applicableLoadMoreClicks = totalNumberOfResults / searchResultCount;
        if ((totalNumberOfResults % searchResultCount) == 0) {
            applicableLoadMoreClicks = applicableLoadMoreClicks - 2;
        } else {
            applicableLoadMoreClicks = applicableLoadMoreClicks - 1;
        }
        log.info("All {} Products Should be displayed after clicking on load More button {} clicks ", totalNumberOfResults, applicableLoadMoreClicks);

        while (applicableLoadMoreClicks > 0) {
            applicableLoadMoreClicks--;
            Thread.sleep(200);
            driver.findElement(loadMoreBtn).click();
            driver.waitForPresenceOfElementLocated(initialProductList);
        }
    }


    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {

    }
}
