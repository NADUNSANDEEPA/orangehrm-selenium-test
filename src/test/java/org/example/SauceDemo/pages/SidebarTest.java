package org.example.SauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.example.utils.DriverManager;

import java.time.Duration;

public class SidebarTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private Sidebar sidebar;
    private WebDriverWait wait;

    @BeforeClass
    public void setupClass() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        sidebar = new Sidebar(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(ConfigLink.SAUCEDEMO_LINK);
        loginPage.login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.urlContains("/inventory.html"));
    }

    @BeforeMethod
    public void beforeEachTest() {
        closeSidebarIfOpen();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
    }

    @AfterMethod
    public void resetAppState() {
        closeSidebarIfOpen();
    }

    @AfterClass
    public void teardown() {
        DriverManager.quitDriver();
    }

    private void closeSidebarIfOpen() {
        try {
            if (!driver.findElements(By.id("react-burger-cross-btn")).isEmpty()) {
                driver.findElement(By.id("react-burger-cross-btn")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("bm-menu")));
            }
        } catch (Exception e) {
            System.out.println("Sidebar already closed or not found");
        }
    }

    @Test(
            priority = 1,
            testName = "Test Case 01 : Click All Items Link",
            description = "Verifies that clicking the 'All Items' link in the sidebar navigates to the inventory page"
    )
    public void testClickAllItemsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        sidebar.openMenu();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_sidebar_link")));
        sidebar.clickAllItems();

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "User should be navigated to the Inventory page.");
    }

    @Test(
            priority = 2,
            testName = "Test Case 02 : Click About Link",
            description = "Verifies that clicking the 'About' link in the sidebar navigates to the Sauce Labs website"
    )
    public void testClickAboutLink() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        sidebar.openMenu();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("about_sidebar_link")));
        String aboutHref = driver.findElement(By.id("about_sidebar_link")).getAttribute("href");

        Assert.assertEquals(aboutHref, "https://saucelabs.com/",
                "The 'About' link should point to the Sauce Labs website.");
    }

    @Test(
            priority = 3,
            testName = "Test Case 03 : Click Logout Link",
            description = "Verifies that clicking the 'Logout' link in the sidebar logs the user out and returns to the login page"
    )
    public void testClickLogoutLink() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        sidebar.openMenu();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        sidebar.clickLogout();

        wait.until(ExpectedConditions.urlContains(ConfigLink.SAUCEDEMO_LINK));
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-test='login-button']")).isDisplayed(),
                "Login button should be visible after logout.");

        loginPage.login("standard_user", "secret_sauce");
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
    }

    @Test(
            priority = 4,
            testName = "Test Case 04 : Click Reset App State Link",
            description = "Verifies that clicking the 'Reset App State' link in the sidebar clears the shopping cart"
    )
    public void testClickResetAppStateLink() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        sidebar.openMenu();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("reset_sidebar_link")));
        sidebar.clickResetAppState();

        Assert.assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty(),
                "Shopping cart should be empty after resetting app state.");
    }
}
