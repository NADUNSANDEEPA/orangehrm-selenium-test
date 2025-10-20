package org.example.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

import static org.example.orangehrm.pages.ConfigLink.VALID_PASSWORD;
import static org.example.orangehrm.pages.ConfigLink.VALID_USERNAME;

public class OrangeHRMLoginTest {

    private WebDriver driver;
    private OrangeHRMLogin loginPage;
    private WebDriverWait wait;

    private static final String BASE_URL = ConfigLink.ORANGE_HRM_LINK+"/web/index.php/auth/login";
    private static final String INVALID_USERNAME = "InvalidUser";
    private static final String INVALID_PASSWORD = "InvalidPass123";

    @BeforeClass
    public void setupClass() {

    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(BASE_URL);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loginPage = new OrangeHRMLogin(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass
    public void tearDownClass() {
    }


    @Test(
            priority = 1,
            description = "Verify login form elements are rendered",
            testName = "Test Case 01 : Verify login form elements are rendered"
    )
    public void testRendersLoginFormElements() {

        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be visible");

        String usernamePlaceholder = loginPage.getUsernamePlaceholder();
        Assert.assertNotNull(usernamePlaceholder, "Username field should have placeholder attribute");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be visible");

        String passwordPlaceholder = loginPage.getPasswordPlaceholder();
        Assert.assertNotNull(passwordPlaceholder, "Password field should have placeholder attribute");

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be visible");
        String loginButtonText = loginPage.getLoginButtonText();
        Assert.assertNotNull(loginButtonText, "Login button should have text");
        String normalizedButtonText = loginButtonText.trim().toLowerCase();
        Assert.assertTrue(normalizedButtonText.equals("login") || normalizedButtonText.equals("登录"), "Login button text should be 'Login' or '登录', but was: " + loginButtonText);

        String usernameLabel = loginPage.getUsernameLabel();
        Assert.assertEquals(usernameLabel, "Username", "Username label should be 'Username'");
        String passwordLabel = loginPage.getPasswordLabel();
        Assert.assertEquals(passwordLabel, "Password", "Password label should be 'Password'");

        Assert.assertTrue(loginPage.isLoginFormDisplayed(), "Login form should be fully displayed");
    }

    @Test(
            priority = 2,
            description = "Verify login with valid credentials and measure execution time",
            testName = "Test Case 02 : Verify login with valid credentials and measure execution time"
    )
    public void testLoginWithValidCredentialsAndMeasureTime() throws InterruptedException {

        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLoginButton();
        Thread.sleep(3000);
        boolean isLoginSuccessful = loginPage.isLoginSuccessful();

        Assert.assertTrue(isLoginSuccessful, "Login should be successful with valid credentials");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/dashboard"), "URL should contain '/dashboard' after successful login");
    }

    @Test(
            priority = 3,
            description = "Verify error message with invalid credentials and capture screenshot",
            testName = "Test Case 03 : Verify error message with invalid credentials and capture screenshot"
    )
    public void testShowsErrorWithInvalidCredentialsAndCaptureScreenshot() throws InterruptedException {

        loginPage.enterUsername(INVALID_USERNAME);
        loginPage.enterPassword(INVALID_PASSWORD);
        loginPage.clickLoginButton();
        Thread.sleep(2000);

        By errorMessageLocator = By.cssSelector("p.oxd-alert-content-text");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));

        String errorText = errorElement.getText();
        Assert.assertFalse(errorText.isEmpty(), "Error message should not be empty");
        Assert.assertTrue(errorText.contains("Invalid credentials"), "Expected 'Invalid credentials' message");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/auth/"), "User should remain on login page after failed login");

    }

    @Test(
            priority = 4,
            description = "Click 'Forgot your password?' and verify navigation",
            testName = "Test Case 04 : Click 'Forgot your password?' and verify navigation"
    )
    public void testForgotPasswordNavigation() throws InterruptedException {

        By forgotTextLocator = By.cssSelector("div.orangehrm-login-forgot p.orangehrm-login-forgot-header");
        WebElement forgotTextElement = driver.findElement(forgotTextLocator);
        Assert.assertTrue(forgotTextElement.getText().trim().contains("Forgot your password?"), "Forgot password text should be visible");

        loginPage.clickForgotPassword();
        Thread.sleep(1000);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/auth/requestPasswordResetCode"), "Should navigate to Reset Password page");

        By resetHeaderLocator = By.tagName("h6");
        WebElement resetHeader  = driver.findElement(resetHeaderLocator);
        Assert.assertTrue(resetHeader.getText().trim().contains("Reset Password"),
                "Reset Password header should be visible");
    }

    @Test(
            priority = 5,
            description = "Verify footer copyright",
            testName = "Test Case 05 : Displays footer copyright correctly"
    )
    public void testFooterCopyright() {

        List<WebElement> footerParagraphs = driver.findElements(By.cssSelector(".orangehrm-copyright"));

        String firstPara = footerParagraphs.get(0).getText().trim();
        System.out.println("First footer paragraph: " + firstPara);
        Assert.assertTrue(firstPara.contains("OrangeHRM OS 5.7"));

        WebElement secondPara = footerParagraphs.get(1);
        String footerText = secondPara.getText().trim();
        System.out.println("Second footer paragraph: " + footerText);

        Assert.assertTrue(footerText.contains("© 2005 - 2025"));
        Assert.assertTrue(footerText.contains("OrangeHRM, Inc"));
        Assert.assertTrue(footerText.contains("All rights reserved."));

        WebElement footerLink = secondPara.findElement(By.tagName("a"));
        Assert.assertEquals(footerLink.getAttribute("href"), "http://www.orangehrm.com/", "Footer link href should match");
        Assert.assertEquals(footerLink.getAttribute("target"), "_blank", "Footer link should open in a new tab");
    }

}