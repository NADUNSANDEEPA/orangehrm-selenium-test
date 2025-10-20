package org.example.parabank.pages;

import org.example.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.UUID;

public class ParabankRegisterPageTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private ParabankRegisterPage registerPage;
    private String uniqueUsername;
    private String password = "Password123!";

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        registerPage = new ParabankRegisterPage(driver);
        uniqueUsername = "user" + UUID.randomUUID().toString().substring(0, 8);
    }

    @BeforeMethod
    public void setupTest() {
        driver.get(CONFIG_LINK.BASE_URL+"/register.htm");
        registerPage.clearFields();
    }

    @AfterClass
    public void teardownClass() {
        DriverManager.quitDriver();
    }

    @Test(priority = 1, description = "Should show errors when submitting empty form")
    public void testEmptyFormShowsErrors() {

        registerPage.clearFields();
        registerPage.clickRegister();

        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        wait.until(driver -> !registerPage.getErrorMessageForField("firstName").isEmpty());

        Assert.assertTrue(registerPage.getErrorMessageForField("firstName").contains("First name is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("lastName").contains("Last name is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("address").contains("Address is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("city").contains("City is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("state").contains("State is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("zipCode").contains("Zip Code is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("ssn").contains("Social Security Number is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("username").contains("Username is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("password").contains("Password is required"));
        Assert.assertTrue(registerPage.getErrorMessageForField("confirmPassword").contains("Password confirmation is required"));
    }

    @Test(priority = 2, description = "Should register successfully with valid data")
    public void testSuccessfulRegistration() {
        registerPage.fillRegistrationFormForBank(
                "John", "Doe", "123 Main St", "New York", "NY",
                "10001", "1234567890", "123-45-6789",
                uniqueUsername, password
        );
        registerPage.clickRegister();

        Assert.assertTrue(driver.getPageSource().contains("Your account was created successfully"));
    }

    @Test(priority = 3, description = "Should show error if username already exists")
    public void testUsernameAlreadyExists() {
        registerPage.fillRegistrationFormForBank(
                "John", "Doe", "123 Main St", "New York", "NY",
                "10001", "1234567890", "123-45-6789",
                uniqueUsername, password
        );
        registerPage.clickRegister();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer.username.errors")));

        String errorText = driver.findElement(By.id("customer.username.errors")).getText();
        System.out.println(errorText);
        Assert.assertTrue(errorText.contains("This username already exists"));
    }
}
