package org.example.parabank.pages;

import org.example.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ParabankSidebarLinksTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private ParabankLoginPage loginPage;
    private ParabankSidebarLinks sidebar;

    private String username = "user" + System.currentTimeMillis();
    private String password = "Password123!";

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new ParabankLoginPage(driver);
        sidebar = new ParabankSidebarLinks(driver);

        ParabankRegisterPage registerPage = new ParabankRegisterPage(driver);
        driver.get(CONFIG_LINK.BASE_URL+"/register.htm");
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
    }

    @BeforeMethod
    public void setupTest() {
        driver.get(CONFIG_LINK.BASE_URL+"/overview.htm");
    }

    @AfterClass
    public void teardownClass() {
        DriverManager.quitDriver();
    }

   @Test(
           priority = 1,
           description = "Verify Open New Account navigates to /openaccount.htm",
           testName = "testOpenNewAccount"
   )
    public void testOpenNewAccountLink() {
        sidebar.clickOpenNewAccount();
        wait.until(ExpectedConditions.urlContains("/openaccount.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/openaccount.htm"));
    }

    @Test(
        priority = 2,
        description = "Verify Accounts Overview navigates to /overview.htm",
        testName = "testAccountsOverview"
    )
    public void testAccountsOverviewLink() {
        sidebar.clickAccountsOverview();
        wait.until(ExpectedConditions.urlContains("/overview.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/overview.htm"));
    }

    @Test(
        priority = 3,
        description = "Verify Transfer Funds navigates to /transfer.htm",
        testName = "testTransferFunds"
    )
    public void testTransferFundsLink() {
        sidebar.clickTransferFunds();
        wait.until(ExpectedConditions.urlContains("/transfer.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/transfer.htm"));
    }

 @Test(
        priority = 4,
        description = "Verify Bill Pay navigates to /billpay.htm",
        testName = "testBillPay"
    )
    public void testBillPayLink() {
        sidebar.clickBillPay();
        wait.until(ExpectedConditions.urlContains("/billpay.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/billpay.htm"));
    }

   @Test(
        priority = 5,
        description = "Verify Find Transactions navigates to /findtrans.htm",
        testName = "testFindTransactions"
    )
    public void testFindTransactionsLink() {
        sidebar.clickFindTransactions();
        wait.until(ExpectedConditions.urlContains("/findtrans.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/findtrans.htm"));
    }

  @Test(
        priority = 6,
        description = "Verify Update Contact Info navigates to /updateprofile.htm",
        testName = "testUpdateContactInfo"
    )
    public void testUpdateContactInfoLink() {
        sidebar.clickUpdateContactInfo();
        wait.until(ExpectedConditions.urlContains("/updateprofile.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/updateprofile.htm"));
    }

   @Test(
        priority = 7,
        description = "Verify Request Loan navigates to /requestloan.htm",
        testName = "testRequestLoan"
    )
    public void testRequestLoanLink() {
        sidebar.clickRequestLoan();
        wait.until(ExpectedConditions.urlContains("/requestloan.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/requestloan.htm"));
    }

   @Test(
        priority = 8,
        description = "Verify Log Out navigates to /index.htm",
        testName = "testLogOut"
    )
    public void testLogOutLink() {
        sidebar.clickLogOut();
        wait.until(ExpectedConditions.urlContains("/index.htm"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/index.htm"));
    }
}
