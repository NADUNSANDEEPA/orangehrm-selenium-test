package org.example.orangehrm.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class OrangeHRMLogin {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    private final By forgotPasswordLink = By.cssSelector("div.orangehrm-login-forgot");

    @FindBy(xpath = "//label[text()='Username']")
    private WebElement usernameLabel;

    @FindBy(xpath = "//label[text()='Password']")
    private WebElement passwordLabel;

    public OrangeHRMLogin(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isLoginFormDisplayed() {
        try {
            return usernameField.isDisplayed() &&
                    passwordField.isDisplayed() &&
                    loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUsernameFieldDisplayed() {
        try {
            return usernameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordFieldDisplayed() {
        try {
            return passwordField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginButtonDisplayed() {
        try {
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameLabel() {
        try {
            return usernameLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordLabel() {
        try {
            return passwordLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.urlContains("/dashboard"));
            return driver.getCurrentUrl().contains("/dashboard");
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginButtonText() {
        try {
            return loginButton.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordPlaceholder() {
        try {
            return passwordField.getAttribute("placeholder");
        } catch (Exception e) {
            return "";
        }
    }

    public String getUsernamePlaceholder() {
        try {
            return usernameField.getAttribute("placeholder");
        } catch (Exception e) {
            return "";
        }
    }

    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }
}
