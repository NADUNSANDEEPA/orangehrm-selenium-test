package org.example.SauceDemo.pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.example.utils.DriverManager;

public class CheckoutTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private Checkout checkout;

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        checkout = new Checkout(driver);
    }

    @BeforeMethod
    public void setupTest() {
        driver.get(CONFIG_LINKS.SAUCEDEMO_LINK);
    }

    @AfterClass
    public void teardownClass() {
        DriverManager.quitDriver();
    }

    @Test(
            testName = "Test Case: Complete Checkout with Valid Information",
            description = "Logs in, adds products to cart, completes checkout with valid info, and verifies order completion"
    )
    public void testCompleteCheckoutWithValidInfo() {
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));

        checkout.addProductToCart("sauce-labs-backpack");
        checkout.addProductToCart("sauce-labs-bike-light");

        checkout.goToCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));

        checkout.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"));

        checkout.enterCheckoutInfo("John", "Doe", "12345");
        checkout.continueCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"));

        Assert.assertTrue(checkout.getCartList().getText().contains("Sauce Labs Backpack"));
        Assert.assertTrue(checkout.getCartList().getText().contains("Sauce Labs Bike Light"));

        checkout.finishCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"));

        Assert.assertEquals(checkout.getCompleteHeaderText(), "Thank you for your order!");
    }
}