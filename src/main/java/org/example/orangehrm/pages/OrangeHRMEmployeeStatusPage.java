package org.example.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrangeHRMEmployeeStatusPage {

    private WebDriver driver;

    public OrangeHRMEmployeeStatusPage(WebDriver driver) {
        this.driver = driver;
    }

    private By saveButton = By.cssSelector("button.oxd-button--secondary.orangehrm-left-space[type='submit']");

    public void fillEmployeeStatusForm(String name) {
        WebElement nameInput = getInputByLabel("Name");
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public String getFieldErrorMessage(String label) {
        return driver.findElement(By.xpath("//label[text()='" + label + "']/following::span[contains(@class,'oxd-input-field-error-message')]"))
                .getText().trim();
    }

    public WebElement getInputByLabel(String labelText) {
        return driver.findElement(By.xpath("//label[text()='" + labelText + "']/following::input[1]"));
    }

    public String getFieldErrorMessageByLabel(String labelName) {
        By errorLocator = By.xpath("//label[text()='" + labelName + "']/following::span[contains(@class,'oxd-input-field-error-message')]");

        WebElement errorElement = driver.findElement(errorLocator);
        return errorElement.getText().trim();
    }
}
