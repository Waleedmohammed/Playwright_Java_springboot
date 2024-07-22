package com.qa.verivox.pages.common.SearchResult;


import com.qa.verivox.core.driverUtils.Driver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchActController extends SearchPage {

    SearchActController(Driver driver) {
        super(driver);
    }

    public SearchActController clickLoadMoreButton() throws Exception {
        if (getTotalNumberOfResults() > 20) {
            driver.findElement(loadMoreBtn).click();
            driver.waitForVisibilityOfElementLocated(loadMoreBtn);
        }
        return this;
    }

    public void clickOnTarifDetailsButton(int productNumber) throws Exception {
        driver.waitForVisibilityOfElementLocated(getProductDetailsBtn(productNumber + 1));
        driver.findElement(getProductDetailsBtn(productNumber + 1)).click();
    }

    public SearchActController scrollTillEndOfPage() throws Exception {
        driver.executeScript("window.scrollTo(0, document.body.scrollHeight);", "");
        return this;
    }
}
