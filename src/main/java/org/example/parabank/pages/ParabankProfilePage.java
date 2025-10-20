package org.example.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ParabankProfilePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By firstNameField = By.id("customer.firstName");
    private By lastNameField = By.id("customer.lastName");
    private By addressField = By.id("customer.address.street");
    private By cityField = By.id("customer.address.city");
    private By stateField = By.id("customer.address.state");
    private By zipField = By.id("customer.address.zipCode");
    private By phoneField = By.id("customer.phoneNumber");
    private By updateButton = By.cssSelector("input[value='Update Profile']");
    private By profileUpdatedMsg = By.xpath("//*[contains(text(),'Profile Updated')]");

    private By openAccountType = By.id("type");
    private By fromAccountSelect = By.id("fromAccountId");
    private By openAccountButton = By.cssSelector("input[value='Open New Account']");

    private By transferAmountField = By.id("amount");

    private By loanAmountField = By.id("amount");
    private By downPaymentField = By.id("downPayment");
    private By loanAccountId = By.id("newAccountId");

    public ParabankProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clearProfileFields() {
        driver.findElement(firstNameField).clear();
        driver.findElement(lastNameField).clear();
        driver.findElement(addressField).clear();
        driver.findElement(cityField).clear();
        driver.findElement(stateField).clear();
        driver.findElement(zipField).clear();
        driver.findElement(phoneField).clear();
    }

    public void updateProfile(String firstName, String lastName, String address, String city, String state, String zip, String phone) {
        clearProfileFields();
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(stateField).sendKeys(state);
        driver.findElement(zipField).sendKeys(zip);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(updateButton).click();
    }

    public boolean isProfileUpdated() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(profileUpdatedMsg)).isDisplayed();
    }

    public void openNewAccount(String accountType) {
        driver.findElement(openAccountType).sendKeys(accountType);
        driver.findElement(fromAccountSelect).click();
        driver.findElement(openAccountButton).click();
    }


    public void transferFunds(String amount) {
        driver.findElement(transferAmountField).clear();
        driver.findElement(transferAmountField).sendKeys(amount);
    }

    public void applyLoan(String amount, String downPayment) {
        driver.findElement(loanAmountField).clear();
        driver.findElement(loanAmountField).sendKeys(amount);
        driver.findElement(downPaymentField).clear();
        driver.findElement(downPaymentField).sendKeys(downPayment);
    }

    public String getLoanAccountNumber() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loanAccountId)).getText();
    }
}