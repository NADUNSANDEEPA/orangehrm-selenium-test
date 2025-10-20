package org.example.SauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class Cart {

    private WebDriver driver;

    public Cart(WebDriver driver) {
        this.driver = driver;
    }

    public void addProductToCart(String productId) {
        driver.findElement(By.id("add-to-cart-" + productId)).click();
    }

    public void goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    public void removeProductFromCart(String productId) {
        driver.findElement(By.id("remove-" + productId)).click();
    }

    public List<String> getProductsInCart() {
        List<WebElement> items = driver.findElements(By.className("inventory_item_name"));
        return items.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isProductInCart(String productName) {
        return getProductsInCart().contains(productName);
    }

    public void clearAllItems() {
        goToCart();

        List<WebElement> removeButtons = driver.findElements(By.xpath("//button[starts-with(@id,'remove-')]"));

        for (WebElement button : removeButtons) {
            if (button.isDisplayed() && button.isEnabled()) {
                button.click();
            }
        }
    }
}