package org.example.SauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.example.utils.DriverManager;

import java.time.Duration;

public class CheckoutStepOneTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private CheckoutStepOne checkoutStepOne;
    private WebDriverWait wait;

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        checkoutStepOne = new CheckoutStepOne(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(CONFIG_LINKS.SAUCEDEMO_LINK);
        loginPage.login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.urlContains("/inventory.html"));

        driver.findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();
        wait.until(ExpectedConditions.urlContains("/cart.html"));

        driver.findElement(By.cssSelector("[data-test='checkout']")).click();
        wait.until(ExpectedConditions.urlContains("/checkout-step-one.html"));
    }

    @BeforeMethod
    public void setupTest() {
//        driver.get(CONFIG_LINKS.SAUCEDEMO_LINK + "/cart.html");
//        wait.until(ExpectedConditions.urlContains("/cart.html"));
//
//        driver.findElement(By.cssSelector("[data-test='checkout']")).click();
//        wait.until(ExpectedConditions.urlContains("/checkout-step-one.html"));
        checkoutStepOne.clearCheckoutForm();
    }


    @AfterClass
    public void teardownClass() {
        DriverManager.quitDriver();
    }

    @Test(
            priority = 1,
            testName = "Test Case 01 : First Name empty shows error",
            description = "First Name empty shows error"
    )
    public void testFirstNameEmptyShowsError() throws InterruptedException {
        checkoutStepOne.fillCheckoutForm("", "Doe", "12345");
        checkoutStepOne.submitCheckoutForm();
        Thread.sleep(1000);
        Assert.assertEquals(checkoutStepOne.getErrorMessage(), "Error: First Name is required");
        Thread.sleep(1000);
    }

    @Test(
            priority = 2,
            testName = "Test Case 02 : Last Name empty shows error",
            description = "Last Name empty shows error"
    )
    public void testLastNameEmptyShowsError() throws InterruptedException {
        checkoutStepOne.fillCheckoutForm("John", "", "12345");
        checkoutStepOne.submitCheckoutForm();
        Thread.sleep(1000);
        Assert.assertEquals(checkoutStepOne.getErrorMessage(), "Error: Last Name is required");
        Thread.sleep(1000);
    }

    @Test(
            priority = 3,
            testName = "Test Case 03 : Postal Code empty shows error",
            description = "Postal Code empty shows error"
    )
    public void testPostalCodeEmptyShowsError() throws InterruptedException {
        checkoutStepOne.fillCheckoutForm("John", "Doe", null);
        checkoutStepOne.submitCheckoutForm();
        Thread.sleep(1000);
        Assert.assertEquals(checkoutStepOne.getErrorMessage(), "Error: Postal Code is required");
        Thread.sleep(1000);
    }

    @Test(
            priority = 4,
            testName = "Test Case 04 : All fields filled proceeds to next step",
            description = "All fields filled proceeds to next step"
    )
    public void testAllFieldsFilledProceedNextStep() throws InterruptedException {
        checkoutStepOne.fillCheckoutForm("John", "Doe", "12345");
        checkoutStepOne.submitCheckoutForm();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.urlContains("/checkout-step-two.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-two.html"));
        Thread.sleep(1000);
    }
}
