package com.qa.verivox.pages.common.Header;

import com.qa.verivox.core.factory.BasePage;
import com.qa.verivox.pages.common.Home.HomeVerifyController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeaderVerifyController extends HeaderPage {
    HeaderVerifyController(BasePage page) {
        super(page);
    }

    public HeaderVerifyController googleIconDisplayed() throws Exception {
        assertTrue(page.isElementVisible(googleIcon));
        return this;
    }

    public HeaderVerifyController searchResultsDisplayed(int Count) throws Exception {
        assertEquals(Count,page.findElementsCountLocatedBy("//div[@class='MjjYud']"));
        return this;
    }

}
