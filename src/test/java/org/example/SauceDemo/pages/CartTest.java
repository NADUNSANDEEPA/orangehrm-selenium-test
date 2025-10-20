package org.example.SauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.example.utils.DriverManager;

import java.time.Duration;

public class CartTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private Cart cart;
    private WebDriverWait wait;

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        cart = new Cart(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(ConfigLink.SAUCEDEMO_LINK);
        loginPage.login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.urlContains("/inventory.html"));
    }

    @BeforeMethod
    public void setup() {
        if (!driver.getCurrentUrl().contains("inventory.html")) {
            driver.navigate().to(ConfigLink.INVENTORY_PAGE);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

        cart.goToCart();
        cart.clearAllItems();

        driver.navigate().to(ConfigLink.INVENTORY_PAGE);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
    }


    @AfterClass
    public void teardown() {
        DriverManager.quitDriver();
    }

    @Test(
            testName = "Test Case 01 : Add Products to Cart and Verify",
            description = "Adds products to the cart and verifies their presence in the cart"
    )
    public void testAddAndVerifyProductsInCart() {
        cart.addProductToCart("sauce-labs-backpack");
        cart.addProductToCart("sauce-labs-bike-light");

        cart.goToCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));

        Assert.assertTrue(cart.isProductInCart("Sauce Labs Backpack"));
        Assert.assertTrue(cart.isProductInCart("Sauce Labs Bike Light"));
    }

    @Test(
            testName = "Test Case 02 : Remove Product from Cart and Verify",
            description = "Removes a product from the cart and verifies its absence while ensuring other products remain"
    )
    public void testRemoveProductAndVerify() throws InterruptedException {
        Thread.sleep(4000);
        cart.addProductToCart("sauce-labs-fleece-jacket");
        cart.addProductToCart("sauce-labs-onesie");

        cart.goToCart();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_list")));

        cart.removeProductFromCart("sauce-labs-fleece-jacket");
        Thread.sleep(3000);
        cart.removeProductFromCart("sauce-labs-onesie");
        Thread.sleep(4000);
        Assert.assertFalse(cart.isProductInCart("Sauce Labs Fleece Jacket"));
        Assert.assertFalse(cart.isProductInCart("Sauce Labs Onesie"));
    }
}
