package org.example.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

import static org.example.orangehrm.pages.ConfigLink.ORANGE_HRM_LINK;

public class OrangeHRMUserAddTest {

    private WebDriver driver;
    private OrangeHRMLogin loginPage;
    private OrangeHRMUserAddPage userAddPage;
    private String username;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new OrangeHRMLogin(driver);
        userAddPage = new OrangeHRMUserAddPage(driver);

        username = "Username@#" + (int) (Math.random() * 1000);


        driver.get(ORANGE_HRM_LINK + "/web/index.php/auth/login");
        loginPage.enterUsername(ConfigLink.VALID_USERNAME);
        loginPage.enterPassword(ConfigLink.VALID_PASSWORD);
        loginPage.clickLoginButton();
    }

    @BeforeMethod
    public void login() {
        driver.get(ORANGE_HRM_LINK+"/web/index.php/admin/saveSystemUser");
    }

    @Test(
            testName = "Test Case 01 : User Save Success",
            priority = 1
    )
    public void testUserSaveSuccess() throws InterruptedException {
        Thread.sleep(3000);
        userAddPage.fillUserForm("Admin", true, "Enabled", username, "StrongPass!123", "StrongPass!123");
        Thread.sleep(3000);
        userAddPage.clickSave();
        Thread.sleep(5000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/viewSystemUsers"));
    }

    @Test(
            testName = "Test Case 02 : User Name Validation - Should be at least 5 characters",
            priority = 2
    )
    public void testUserNameMinLengthValidation() throws InterruptedException {
        Thread.sleep(3000);
        userAddPage.fillUserForm("Admin", true, "Enabled", "i", "StrongPass!123", "StrongPass!123");
        Thread.sleep(3000);
        userAddPage.clickSave();

        String errorText = userAddPage.getErrorMessageByLabel("Username");
        Assert.assertTrue(errorText.contains("at least 5 characters"));
    }

    @Test(
            testName = "Test Case 03 : User Name Validation - Already exists",
            priority = 3
    )
    public void testUserNameAlreadyExists() throws InterruptedException {
        Thread.sleep(3000);
        userAddPage.fillUserForm("Admin", true, "Enabled", username, "StrongPass!123", "StrongPass!123");
        Thread.sleep(3000);
        userAddPage.clickSave();

        String errorText = userAddPage.getErrorMessageByLabel("Username");
        System.out.println(errorText);
        Assert.assertTrue(errorText.contains("Already exists"));
    }

    @Test(
            testName = "Test Case 04 : Password Validation",
            priority = 4
    )
    public void testPasswordValidation() throws InterruptedException {
        Thread.sleep(3000);
        userAddPage.fillUserForm("Admin", true, "Enabled", "A8DCo4Ys010Z", "12", "2");
        Thread.sleep(3000);
        userAddPage.clickSave();

        String passLengthError = userAddPage.getErrorMessageByLabel("Password");
        String passMatchError = userAddPage.getErrorMessageByLabel("Confirm Password");

        Assert.assertTrue(passLengthError.contains("at least 7 characters"));
        Assert.assertTrue(passMatchError.contains("Passwords do not match"));
    }

    @Test(
            testName = "Test Case 05 : Employee Name Validation - Invalid",
            priority = 5
    )
    public void testEmployeeNameInvalid() throws InterruptedException {
        Thread.sleep(3000);
        userAddPage.fillUserForm("Admin", false, "Enabled", "User" + (int)(Math.random()*1000), "StrongPass!123", "StrongPass!123");
        Thread.sleep(3000);
        userAddPage.clickSave();

        String errorText = userAddPage.getErrorMessageByLabel("Employee Name");
        Assert.assertTrue(errorText.contains("Required"));
    }

    @Test(
            testName = "Test Case 06 : Shows required field errors when submitted empty",
            priority = 6
    )
    public void testRequiredFieldErrors() throws InterruptedException {
        Thread.sleep(3000);
        userAddPage.fillUserForm("", false, "", "", "", "");
        Thread.sleep(3000);
        userAddPage.clickSave();

        Assert.assertTrue(userAddPage.getErrorMessageByLabel("User Role").contains("Required"));
        Assert.assertTrue(userAddPage.getErrorMessageByLabel("Employee Name").contains("Required"));
        Assert.assertTrue(userAddPage.getErrorMessageByLabel("Status").contains("Required"));
        Assert.assertTrue(userAddPage.getErrorMessageByLabel("Username").contains("Required"));
        Assert.assertTrue(userAddPage.getErrorMessageByLabel("Password").contains("Required"));
        Assert.assertTrue(userAddPage.getErrorMessageByLabel("Confirm Password").contains("Passwords do not match"));
    }

    @Test(
            testName = "Test Case 07 : Is User List Retrieve",
            priority = 7
    )
    public void testUserListRetrieve() {
        driver.get(ORANGE_HRM_LINK+"/web/index.php/admin/viewSystemUsers");
        Assert.assertTrue(driver.findElement(By.cssSelector(".oxd-table")).isDisplayed());
        Assert.assertTrue(driver.findElements(By.cssSelector(".oxd-table-body .oxd-table-row")).size() >= 1);
    }

    @AfterClass
    public void tearDown() {
        if(driver != null) driver.quit();
    }
}