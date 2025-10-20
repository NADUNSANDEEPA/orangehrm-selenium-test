package org.example.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrangeHRMJobTitlePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrangeHRMJobTitlePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement findInputByLabel(String labelText) {
        return driver.findElement(By.xpath("//label[text()='" + labelText + "']/following::input[1]"));
    }

    private WebElement findTextareaByLabel(String labelText) {
        return driver.findElement(By.xpath("//label[text()='" + labelText + "']/following::textarea[1]"));
    }

    private WebElement findFileInputByLabel(String labelText) {
        return driver.findElement(By.xpath("//label[text()='" + labelText + "']/following::input[@type='file'][1]"));
    }

    private By saveButton = By.xpath("//button[@type='submit' and contains(@class,'oxd-button--secondary')]");
    private By requiredErrorMessage = By.cssSelector(".oxd-input-field-error-message");

    public void enterJobTitle(String title) {
        WebElement input = findInputByLabel("Job Title");
        input.clear();
        input.sendKeys(title);
    }

    public void enterJobDescription(String description) {
        WebElement textarea = findTextareaByLabel("Job Description");
        textarea.clear();
        textarea.sendKeys(description);
    }

    public void uploadJobSpecification(String filePath) {
        WebElement fileInput = findFileInputByLabel("Job Specification");
        fileInput.sendKeys(filePath);
    }

    public void enterNote(String note) {
        WebElement textarea = findTextareaByLabel("Note");
        textarea.clear();
        textarea.sendKeys(note);
    }

    public void clickSave() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        button.click();
    }

    public String getRequiredErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredErrorMessage)).getText().trim();
    }

    public void fillJobForm(String title, String description, String filePath, String note) throws InterruptedException {
        enterJobTitle(title);
        Thread.sleep(3000);
        enterJobDescription(description);
        Thread.sleep(3000);
        uploadJobSpecification(filePath);
        Thread.sleep(3000);
        enterNote(note);
    }
}
