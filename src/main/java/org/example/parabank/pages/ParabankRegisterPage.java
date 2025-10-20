package org.example.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ParabankRegisterPage {

    private WebDriver driver;

    private By firstNameField = By.id("customer.firstName");
    private By lastNameField = By.id("customer.lastName");
    private By addressField = By.id("customer.address.street");
    private By cityField = By.id("customer.address.city");
    private By stateField = By.id("customer.address.state");
    private By zipCodeField = By.id("customer.address.zipCode");
    private By phoneField = By.id("customer.phoneNumber");
    private By ssnField = By.id("customer.ssn");
    private By usernameField = By.id("customer.username");
    private By passwordField = By.id("customer.password");
    private By confirmPasswordField = By.id("repeatedPassword");
    private By registerButton = By.cssSelector("input[type='submit'][value='Register']");

    // Error messages
    private By errorFirstName = By.id("customer.firstName.errors");
    private By errorLastName = By.id("customer.lastName.errors");
    private By errorAddress = By.id("customer.address.street.errors");
    private By errorCity = By.id("customer.address.city.errors");
    private By errorState = By.id("customer.address.state.errors");
    private By errorZipCode = By.id("customer.address.zipCode.errors");
    private By errorSSN = By.id("customer.ssn.errors");
    private By errorUsername = By.id("customer.username.errors");
    private By errorPassword = By.id("customer.password.errors");
    private By errorConfirmPassword = By.id("repeatedPassword.errors");


    public ParabankRegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillRegistrationFormForBank(String firstName, String lastName, String address, String city, String state,
                                     String zipCode, String phone, String ssn, String username, String password) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(stateField).sendKeys(state);
        driver.findElement(zipCodeField).sendKeys(zipCode);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(ssnField).sendKeys(ssn);
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public String getErrorMessageForField(String fieldName) {
        switch (fieldName) {
            case "firstName": return driver.findElement(errorFirstName).getText();
            case "lastName": return driver.findElement(errorLastName).getText();
            case "address": return driver.findElement(errorAddress).getText();
            case "city": return driver.findElement(errorCity).getText();
            case "state": return driver.findElement(errorState).getText();
            case "zipCode": return driver.findElement(errorZipCode).getText();
            case "ssn": return driver.findElement(errorSSN).getText();
            case "username": return driver.findElement(errorUsername).getText();
            case "password": return driver.findElement(errorPassword).getText();
            case "confirmPassword": return driver.findElement(errorConfirmPassword).getText();
            default: return "";
        }
    }

    public void clearFields() {
        driver.findElement(firstNameField).clear();
        driver.findElement(lastNameField).clear();
        driver.findElement(addressField).clear();
        driver.findElement(cityField).clear();
        driver.findElement(stateField).clear();
        driver.findElement(zipCodeField).clear();
        driver.findElement(phoneField).clear();
        driver.findElement(ssnField).clear();
        driver.findElement(usernameField).clear();
        driver.findElement(passwordField).clear();
        driver.findElement(confirmPasswordField).clear();
    }
}
