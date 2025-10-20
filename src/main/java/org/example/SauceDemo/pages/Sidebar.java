package org.example.SauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Sidebar {
    private WebDriver driver;
    private By menuButton = By.id("react-burger-menu-btn");
    private By allItemsLink = By.id("inventory_sidebar_link");
    private By aboutLink = By.id("about_sidebar_link");
    private By logoutLink = By.id("logout_sidebar_link");
    private By resetAppStateLink = By.id("reset_sidebar_link");

    public Sidebar(WebDriver driver) {
        this.driver = driver;
    }

    public void openMenu() {
        driver.findElement(menuButton).click();
    }

    public void clickAllItems() {
        driver.findElement(allItemsLink).click();
    }

    public void clickAbout() {
        driver.findElement(aboutLink).click();
    }

    public void clickLogout() {
        driver.findElement(logoutLink).click();
    }

    public void clickResetAppState() {
        driver.findElement(resetAppStateLink).click();
    }
}

