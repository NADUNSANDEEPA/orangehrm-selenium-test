package org.example.helper.FormHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FormHelper {

    private WebDriver driver;

    public FormHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void selectFirstDropdownOptionByLabel(String labelText) throws InterruptedException {

        WebElement dropdown = driver.findElement(By.xpath(
                "//label[text()='" + labelText + "']/following::div[contains(@class,'oxd-select-text')][1]"
        ));

        WebElement arrow = dropdown.findElement(By.cssSelector("i.oxd-icon"));
        arrow.click();
        Thread.sleep(2000);

        WebElement firstOption = driver.findElement(By.xpath("//div[@role='listbox']/div[2]"));
        firstOption.click();
        Thread.sleep(1000);
    }


    public void selectFirstAutocompleteOptionByLabel(String labelText, String inputText) throws InterruptedException {

        WebElement input = driver.findElement(By.xpath(
                "//label[text()='" + labelText + "']/following::input[1]"
        ));
        input.clear();
        input.sendKeys(inputText);
        Thread.sleep(2000); // wait for suggestions to appear

        WebElement firstSuggestion = driver.findElement(By.xpath(
                "//div[contains(@class,'oxd-autocomplete-wrapper')]//div[@role='option'][2]"
        ));
        firstSuggestion.click();
        Thread.sleep(1000);
    }

    public WebElement getInputByLabel(String labelText) {
        String xpath = "//label[text()='" + labelText + "']/following::input[1] | " +
                "//label[text()='" + labelText + "']/following::textarea[1]";
        return driver.findElement(By.xpath(xpath));
    }
}
