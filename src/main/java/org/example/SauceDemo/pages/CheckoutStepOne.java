package org.example.SauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutStepOne {

    private WebDriver driver;

    private By firstNameField = By.cssSelector("[data-test='firstName']");
    private By lastNameField = By.cssSelector("[data-test='lastName']");
    private By postalCodeField = By.cssSelector("[data-test='postalCode']");
    private By continueButton = By.cssSelector("[data-test='continue']");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutStepOne(WebDriver driver) {
        this.driver = driver;
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        if (firstName != null) {
            driver.findElement(firstNameField).clear();
            driver.findElement(firstNameField).sendKeys(firstName);
        }
        if (lastName != null) {
            driver.findElement(lastNameField).clear();
            driver.findElement(lastNameField).sendKeys(lastName);
        }
        if (postalCode != null) {
            driver.findElement(postalCodeField).clear();
            driver.findElement(postalCodeField).sendKeys(postalCode);
        }
    }

    public void submitCheckoutForm() {
        driver.findElement(continueButton).click();
    }

    public String getErrorMessage() {
        try {
            WebElement error = driver.findElement(errorMessage);
            return error.getText().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public void clearCheckoutForm() {
        driver.findElement(firstNameField).clear();
        driver.findElement(lastNameField).clear();
        driver.findElement(postalCodeField).clear();
    }
}
