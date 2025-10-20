package org.example.SauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Checkout {
    private WebDriver driver;

    public Checkout(WebDriver driver) {
        this.driver = driver;
    }

    public void addProductToCart(String productId) {
        driver.findElement(By.id("add-to-cart-" + productId)).click();
    }

    public void goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    public void clickCheckout() {
        driver.findElement(By.cssSelector("[data-test='checkout']")).click();
    }

    public void enterCheckoutInfo(String firstName, String lastName, String postalCode) {
        driver.findElement(By.cssSelector("[data-test='firstName']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("[data-test='lastName']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("[data-test='postalCode']")).sendKeys(postalCode);
    }

    public void continueCheckout() {
        driver.findElement(By.cssSelector("[data-test='continue']")).click();
    }

    public WebElement getCartList() {
        return driver.findElement(By.className("cart_list"));
    }

    public void finishCheckout() {
        driver.findElement(By.cssSelector("[data-test='finish']")).click();
    }

    public String getCompleteHeaderText() {
        return driver.findElement(By.cssSelector("[data-test='complete-header']")).getText();
    }
}
