package org.example.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ParabankSidebarLinks {

    private WebDriver driver;

    public ParabankSidebarLinks(WebDriver driver) {
        this.driver = driver;
    }

    private By openNewAccountLink = By.linkText("Open New Account");
    private By accountsOverviewLink = By.linkText("Accounts Overview");
    private By transferFundsLink = By.linkText("Transfer Funds");
    private By billPayLink = By.linkText("Bill Pay");
    private By findTransactionsLink = By.linkText("Find Transactions");
    private By updateContactInfoLink = By.linkText("Update Contact Info");
    private By requestLoanLink = By.linkText("Request Loan");
    private By logOutLink = By.linkText("Log Out");

    public void clickOpenNewAccount() {
        driver.findElement(openNewAccountLink).click();
    }

    public void clickAccountsOverview() {
        driver.findElement(accountsOverviewLink).click();
    }

    public void clickTransferFunds() {
        driver.findElement(transferFundsLink).click();
    }

    public void clickBillPay() {
        driver.findElement(billPayLink).click();
    }

    public void clickFindTransactions() {
        driver.findElement(findTransactionsLink).click();
    }

    public void clickUpdateContactInfo() {
        driver.findElement(updateContactInfoLink).click();
    }

    public void clickRequestLoan() {
        driver.findElement(requestLoanLink).click();
    }

    public void clickLogOut() {
        driver.findElement(logOutLink).click();
    }

    public boolean isLinkVisible(By linkLocator) {
        WebElement link = driver.findElement(linkLocator);
        return link.isDisplayed();
    }
}