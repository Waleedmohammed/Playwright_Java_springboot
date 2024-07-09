package com.qa.verivox.pages.common.SearchResult;

import com.qa.verivox.core.driverUtils.Driver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class SearchVerifyController extends SearchPage {

    private int expectedProductCount = 0;

    private List<String> productDetails = new ArrayList<>();

    SearchVerifyController(Driver driver) {
        super(driver);
    }

    public SearchVerifyController tarrifsShownSuccessfully() throws Exception {
        driver.waitForVisibilityOfElementLocated(productContainer);
        assertTrue(driver.findElements(productContainer).size() >= 5, "It is expected to have at least 5 products ... , Something Wrong in search functionality");
        return this;
    }

    public SearchVerifyController totalNumberOfResultsDisplayed() throws Exception {
        assertTrue(driver.findElement(totalNumberResultLbl).isDisplayed(), "Total Number of Results is not visible");
        assertTrue(getTotalNumberOfResults() > 0, "Total Number of Results Zero !!");
        return this;
    }

    public SearchVerifyController isSearchResultCount(int expectedProductCount) throws Exception {
        this.expectedProductCount = this.expectedProductCount + expectedProductCount;
        int displayedSearchResult = driver.findElements(initialProductList).size();

        log.info("Displayed Products count is {}", displayedSearchResult);
        log.info("Expected Products count is {}", this.expectedProductCount);
        Assertions.assertEquals(displayedSearchResult, this.expectedProductCount, "Displayed Products should be " + this.expectedProductCount + " products but it is " + displayedSearchResult + " product(s)");
        return this;
    }

    public SearchVerifyController iCanClickLoadMoreButtonTillAllProductsDisplayed(int searchResultCount) throws Exception {

        if (totalNumberOfResults > searchResultCount) {
            clickLoadMoreButtonIfApplicable(searchResultCount);
        }
        int displayedSearchResult = driver.findElements(initialProductList).size();
        log.info("Displayed Products count is {}", displayedSearchResult);
        log.info("Total Products count is {}", totalNumberOfResults);
        Assertions.assertEquals(totalNumberOfResults, displayedSearchResult, "Displayed Products should be " + totalNumberOfResults + " products but it is " + displayedSearchResult + " product(s)");
        Assertions.assertFalse(driver.isElementExist(loadMoreBtn), "Load More Button still visible after loading all total products !!");

        return this;
    }

    public SearchVerifyController iCanSeePriceOfProductNumber(int productNumber) throws Exception {

        assertTrue(driver.findElement(getProductPriceLbl(productNumber + 1)).isDisplayed(), "Price for product Number " + productNumber + " is not displayed");
        String productPrice = driver.findElement(getProductPriceLbl(productNumber + 1)).getText().trim();
        log.info("Price for Product Number {} is {}", productNumber, productPrice);
        return this;
    }

    public SearchVerifyController iCanSeeProductDetails(List<String> expectedProductDetails) throws Exception {

        if (productDetails.isEmpty()) {
            getListOfProductDetails();
        }

        for (String expectedProductDetail : expectedProductDetails) {
            Assertions.assertTrue(productDetails.contains(expectedProductDetail.trim()), "It is expected to see " + expectedProductDetail.trim() + " But it is not found !");
        }

        return this;
    }

    public SearchVerifyController onlineAntragButtonDisplayed(int productNumber) {

        Assertions.assertAll(
                () -> driver.findElement(getOnlineAngeboteBtn(productNumber)).isDisplayed(),
                () -> driver.findElement(getOnlineAngeboteBtn(productNumber)).isElementClickable()
        );
        return this;
    }

    private List<String> getListOfProductDetails() throws InterruptedException {
        productDetails = driver.getAllStringsInsideUnOrderListLocatedBy(productDetailsList);
        log.info("Visible Product Details Info are {} ", productDetails);
        return productDetails;
    }
}
