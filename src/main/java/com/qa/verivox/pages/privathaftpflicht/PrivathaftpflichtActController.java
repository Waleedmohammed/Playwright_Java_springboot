package com.qa.verivox.pages.privathaftpflicht;

import com.qa.verivox.core.driverUtils.Driver;
import com.qa.verivox.utils.TestHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrivathaftpflichtActController extends PrivathaftpflichtPage {

    PrivathaftpflichtActController(Driver driver) {
        super(driver);
    }


    public PrivathaftpflichtActController selectFamilistand(String familistandValue) throws Exception {
        driver.findElement(familistandDdl).selectValueFromDDL(familistandValue);
        return this;
    }

    public PrivathaftpflichtActController enterAge(int age) throws Exception {
        driver.findElement(ageTxtBox).enterText(Integer.toString(age));
        return this;
    }

    public PrivathaftpflichtActController clickJetztVergleichenButton() throws Exception {
        driver.findElement(submitBtn).click();
        return this;
    }

    public PrivathaftpflichtActController enterBirthDate(String birthDate) throws Exception {
        if (TestHelper.isDateFormatIsCorrect("^\\d{2}.\\d{2}.\\d{4}$", birthDate)) {
            driver.findElement(geburtsdatumTxtBox).enterText("21.09.1988");
        } else {
            throw new Exception("Entered Birthdate is not matching expected format");
        }

        return this;
    }

    public PrivathaftpflichtActController enterPostalCode(String postalCode) throws Exception {
        driver.findElement(postalCodeTxtBox).enterText(postalCode);
        return this;
    }

    public PrivathaftpflichtActController clickOnVergleichenButton() throws Exception {

        driver.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        driver.waitForVisibilityOfElementLocated(vergleichenBtn);
        driver.findElement(vergleichenBtn).click();
        return this;
    }





/*    public void openProductNumber(int productNumber) throws Exception {
        driver.waitForVisibilityOfElementLocated(getProductPriceBtn(productNumber + 1));
        driver.findElement(getProductPriceBtn(productNumber + 1)).click();
    }*/
}
