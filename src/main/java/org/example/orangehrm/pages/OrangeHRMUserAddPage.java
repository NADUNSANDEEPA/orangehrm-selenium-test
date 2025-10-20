package org.example.orangehrm.pages;

import org.example.helper.FormHelper.FormHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OrangeHRMUserAddPage {

    private WebDriver driver;
    FormHelper formHelper;

    public OrangeHRMUserAddPage(WebDriver driver) {
        this.driver = driver;
        this.formHelper = new FormHelper(driver);
    }

    private By saveButton = By.cssSelector("button[type='submit']");


    public void fillUserForm(String userRole, boolean validEmployeeName, String status, String username,
                             String password, String confirmPassword) throws InterruptedException {

        if (!userRole.isEmpty()){
            formHelper.selectFirstDropdownOptionByLabel("User Role");
        }

        if (validEmployeeName) {
            formHelper.selectFirstAutocompleteOptionByLabel("Employee Name","a");
        }

        if (!status.isEmpty()){
            formHelper.selectFirstDropdownOptionByLabel("Status");
        }

        WebElement usernameInput = formHelper.getInputByLabel("Username");
        usernameInput.clear();
        usernameInput.sendKeys(username);

        Thread.sleep(3000);
        WebElement passInput = formHelper.getInputByLabel("Password");
        passInput.clear();
        passInput.sendKeys(password);

        WebElement cpassInput = formHelper.getInputByLabel("Confirm Password");
        cpassInput.clear();
        cpassInput.sendKeys(confirmPassword);
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public String getErrorMessageByLabel(String labelText) {
        String xpath = "//label[text()='" + labelText + "']/following::span[contains(@class,'oxd-input-field-error-message')]";
        List<WebElement> errors = driver.findElements(By.xpath(xpath));
        if (errors.size() > 0) return errors.get(0).getText().trim();
        return "";
    }
}
