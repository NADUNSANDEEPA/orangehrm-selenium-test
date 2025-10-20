package org.example.parabank.pages;


import org.example.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ParabankProfilePageTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private ParabankLoginPage loginPage;
    private ParabankSidebarLinks sidebar;
    private ParabankProfilePage profilePage;

    private String username = "user" + System.currentTimeMillis();
    private String password = "Password123!";

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new ParabankLoginPage(driver);
        sidebar = new ParabankSidebarLinks(driver);

        ParabankRegisterPage registerPage = new ParabankRegisterPage(driver);
        driver.get(ConfigLink.BASE_URL + "/register.htm");
        registerPage.fillRegistrationFormForBank(
                "John", "Doe", "123 Main St", "New York", "NY",
                "10001", "1234567890", "123-45-6789",
                username, password
        );
        registerPage.clickRegister();

        wait.until(ExpectedConditions.urlContains("/register.htm"));
        sidebar.clickLogOut();
        wait.until(ExpectedConditions.urlContains("/index.htm"));

        loginPage.login(username, password);
        profilePage = new ParabankProfilePage(driver);
    }

    @BeforeMethod
    public void setupTest() {
        driver.get(ConfigLink.BASE_URL + "/updateprofile.htm");
    }

    @AfterClass
    public void teardownClass() {
        DriverManager.quitDriver();
    }


    @Test(
            priority = 1,
            testName = "Test Case 01: Profile update with empty fields",
            description = "Submitting empty profile fields should not update profile and should remain on the update profile page"
    )
    public void testProfileUpdateEmptyFields() {
        driver.navigate().to(ConfigLink.BASE_URL + "/updateprofile.htm");
        profilePage.clearProfileFields();
        driver.findElement(By.cssSelector("input[value='Update Profile']")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("updateprofile.htm"));
    }


    @Test(
            priority = 2,
            description = "Update profile with valid data",
            testName = "Test Case 02: Profile update with valid data"
    )
    public void testProfileUpdateValidData() {
        profilePage.updateProfile("Jane", "Smith", "456 Green Road", "Kandy", "Central", "20000", "0771234567");
        Assert.assertTrue(profilePage.isProfileUpdated());
    }


    @Test(
            priority = 3,
            description = "Open new account and verify the account number",
            testName = "Test Case 03: Open new account and verify the account number"
    )
    public void testOpenNewAccount() throws InterruptedException {
        driver.navigate().to(ConfigLink.BASE_URL + "/openaccount.htm");

        profilePage.openNewAccount("0");
        Thread.sleep(2000);

        Select select = new Select(driver.findElement(By.id("fromAccountId")));
        String selectedAccount = select.getFirstSelectedOption().getAttribute("value");
        System.out.println("Selected From Account: " + selectedAccount);

        driver.findElement(By.cssSelector("input[type='button'][value='Open New Account']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("openAccountResult")));

        String title = driver.findElement(By.cssSelector("#openAccountResult h1.title")).getText();
        Assert.assertEquals(title, "Account Opened!");

        String message = driver.findElement(By.cssSelector("#openAccountResult p")).getText();
        Assert.assertTrue(message.contains("Congratulations, your account is now open."));

        String newAccountNumber = driver.findElement(By.id("newAccountId")).getText();
        System.out.println("New Account Number: " + newAccountNumber);

        Assert.assertTrue(newAccountNumber.matches("\\d+"), "Account number should be numeric");
    }


    @Test(
            priority = 4,
            description = "Transfer funds and verify the result",
            testName = "Test Case 04: Transfer funds and verify the result"
    )
    public void testTransferFunds() throws InterruptedException {
        driver.navigate().to(ConfigLink.BASE_URL + "/transfer.htm");
        profilePage.transferFunds("22");

        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[type='submit'][value='Transfer']")).click();

        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showResult")));

        String title = driver.findElement(By.cssSelector("#showResult h1.title")).getText();
        Assert.assertEquals(title, "Transfer Complete!");

        String amountTransferred = driver.findElement(By.id("amountResult")).getText();
        Assert.assertEquals(amountTransferred, "$22.00");

        String fromAccount = driver.findElement(By.id("fromAccountIdResult")).getText();
        String toAccount = driver.findElement(By.id("toAccountIdResult")).getText();
        System.out.println("Transferred from account: " + fromAccount + " to account: " + toAccount);
    }

//    @Test(priority = 5)
//    public void testLoanApproved() {
//        profilePage.applyLoan("50", "10");
//        Assert.assertTrue(profilePage.isLoanApproved());
//        String loanAccount = profilePage.getLoanAccountNumber();
//        Assert.assertTrue(loanAccount.matches("\\d+"));
//    }
//
//    @Test(priority = 6)
//    public void testLoanDenied() {
//        profilePage.applyLoan("5000", "100000");
//        Assert.assertTrue(profilePage.isLoanDenied());
//    }
}