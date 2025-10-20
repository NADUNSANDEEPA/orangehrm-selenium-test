package org.example.SauceDemo.pages;


import org.example.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginPageTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
    }

    @BeforeMethod
    public void setupTest() {
        driver.get(ConfigLink.SAUCEDEMO_LINK);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void cleanup() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void teardownClass() {
        DriverManager.quitDriver();
    }

    @Test(
            testName = "Test Case 01: Logs in successfully with standard_user",
            description = "Logs in successfully with standard_user and verifies inventory page"
    )
    public void testStandardUserLogin() {
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test(
            testName = "Test Case 02: Displays error for locked_out_user",
            description = "Test Case 02: Displays error for locked_out_user"
    )
    public void testLockedOutUser() {
        loginPage.login("locked_out_user", "secret_sauce");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Epic sadface: Sorry, this user has been locked out."));
    }

    @Test(
            testName =  "Test Case 03: Displays error for invalid credentials",
            description = "Test Case 03: Displays error for invalid credentials"
    )
    public void testInvalidCredentials() {
        loginPage.login("invalid_user", "wrong_password");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username and password do not match any user"));
    }

    @Test(
            testName = "Test Case 04: Displays error for empty username and password fields",
            description = "Test Case 04: Displays error for empty username and password fields"
    )
    public void testEmptyFields() {
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username is required"));
    }
}