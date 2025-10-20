package org.example.orangehrm.pages;

import org.example.helper.FormHelper.FormHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrangeHRMRecruitmentPage {

    private WebDriver driver;
    private FormHelper formHelper;

    public OrangeHRMRecruitmentPage(WebDriver driver) {
        this.driver = driver;
        this.formHelper = new FormHelper(driver);
    }

    public void navigateToAddVacancy(String link) {
        driver.get(link);
    }

    public void fillVacancyForm(String vacancyName, boolean selectJobTitle, String description,
                                boolean manager, String positions) throws InterruptedException {

        WebElement vacancyInput = formHelper.getInputByLabel("Vacancy Name");
        vacancyInput.clear();
        vacancyInput.sendKeys(vacancyName);

        if (selectJobTitle) {
            formHelper.selectFirstDropdownOptionByLabel("Job Title");
        }

        WebElement descriptionInput = formHelper.getInputByLabel("Description");
        descriptionInput.clear();
        descriptionInput.sendKeys(description);

        if(manager){
            formHelper.selectFirstAutocompleteOptionByLabel("Hiring Manager","a");
        }

        WebElement positionsInput = formHelper.getInputByLabel("Number of Positions");
        positionsInput.clear();
        positionsInput.sendKeys(positions);
    }



    public void clickSave() {
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    public String getDuplicateVacancyMessage() {
        return driver.findElement(By.cssSelector(".oxd-input-group__message")).getText();
    }

    public String getFieldErrorMessageByLabel(String labelName) {
        By errorLocator = By.xpath("//label[text()='" + labelName + "']/following::span[contains(@class,'oxd-input-field-error-message')]");

        WebElement errorElement = driver.findElement(errorLocator);
        return errorElement.getText().trim();
    }
}