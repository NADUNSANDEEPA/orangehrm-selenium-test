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

public class ParabankLoginPageTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private ParabankLoginPage loginPage;
    private ParabankRegisterPage registerPage;
    private String uniqueUsername;
    private String password = "Password123!";

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new ParabankLoginPage(driver);
        registerPage = new ParabankRegisterPage(driver);
        uniqueUsername = "user" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    @BeforeMethod
    public void setupTest() {
        driver.get(ConfigLink.BASE_URL+"/index.htm");
        loginPage.clearFields();
    }

    @AfterClass
    public void teardownClass() {
        DriverManager.quitDriver();
    }

    @Test(priority = 1, description = "Should show error when submitting empty login form")
    public void testEmptyLoginShowsError() throws InterruptedException {
        driver.findElement(By.cssSelector("input[type='submit'][value='Log In']")).click();
        Thread.sleep(2000);
        String errorText = driver.findElement(By.cssSelector("#rightPanel .error")).getText();
        Assert.assertTrue(errorText.contains("Please enter a username and password."));
    }

    @Test(priority = 2, description = "Should show error for invalid credentials")
    public void testInvalidCredentialsShowError() {
        loginPage.login("nadunee", "wrongPassword");
        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(errorText.contains("The username and password could not be verified."));
    }

    @Test(priority = 3, description = "Should register and login successfully with valid credentials")
    public void testSuccessfulRegistrationAndLogin() {

        driver.get(ConfigLink.BASE_URL+"/register.htm");

        registerPage.fillRegistrationFormForBank(
                "John", "Doe", "123 Main St", "New York", "NY",
                "10001", "1234567890", "123-45-6789",
                uniqueUsername, password
        );

        registerPage.clickRegister();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Log Out']"))).click();

        loginPage.login(uniqueUsername, password);
        wait.until(ExpectedConditions.urlContains("/overview.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/overview.htm"));
    }
}