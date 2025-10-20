package org.example.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LinkCheck {

    private WebDriver driver;
    private WebDriverWait wait;

    private By mainMenuItemsLocator = By.cssSelector(".oxd-main-menu-item");

    public LinkCheck(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<WebElement> getMenuItems() {
        return driver.findElements(mainMenuItemsLocator);
    }

    public WebElement getMenuItemByName(String name) {
        return getMenuItems().stream()
                .filter(item -> item.getText().trim().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu item not found: " + name));
    }

    public void clickMenuItem(String name) {
        WebElement menu = getMenuItemByName(name);
        wait.until(ExpectedConditions.elementToBeClickable(menu)).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean pageHasNo404() {
        String bodyText = driver.findElement(By.tagName("body")).getText();
        List<WebElement> errorCodeElements = driver.findElements(By.cssSelector(".error-code"));
        return !bodyText.contains("HTTP ERROR 404") && errorCodeElements.isEmpty();
    }
}
