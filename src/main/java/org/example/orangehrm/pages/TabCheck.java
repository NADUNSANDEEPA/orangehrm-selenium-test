package org.example.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TabCheck {

    private WebDriver driver;
    private WebDriverWait wait;

    private By tabsLocator = By.cssSelector(".orangehrm-tabs-item");

    public TabCheck(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToPersonalDetails(String baseUrl, int empNumber) {
        driver.get(baseUrl + "/web/index.php/pim/viewPersonalDetails/empNumber/" + empNumber);
        wait.until(ExpectedConditions.visibilityOfElementLocated(tabsLocator));
    }

    public List<WebElement> getTabs() {
        return driver.findElements(tabsLocator);
    }

    public WebElement getTabByName(String tabName) {
        return getTabs().stream()
                .filter(tab -> tab.getText().trim().equals(tabName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tab not found: " + tabName));
    }

    public void clickTab(String tabName) {
        WebElement tab = getTabByName(tabName);
        wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
