package org.example.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Random;

import static org.example.orangehrm.pages.ConfigLink.*;

public class OrangeHRMEmployeeStatusTest {

    private WebDriver driver;
    private OrangeHRMLogin loginPage;
    private OrangeHRMEmployeeStatusPage empStatusPage;
    private String empStatus;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new OrangeHRMLogin(driver);
        empStatusPage = new OrangeHRMEmployeeStatusPage(driver);

        empStatus = "EmpStatus-" + new Random().nextInt(1000);

        driver.get(ORANGE_HRM_LINK + "/web/index.php/auth/login");
        loginPage.enterUsername(ConfigLink.VALID_USERNAME);
        loginPage.enterPassword(ConfigLink.VALID_PASSWORD);
        loginPage.clickLoginButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"));
    }

    @BeforeMethod
    public void loginAndNavigate() {
        driver.get(ORANGE_HRM_LINK + "/web/index.php/admin/saveEmploymentStatus");
    }

    @Test(
            priority = 1,
            testName = "Test Case 01 : Save employee status with valid data"
    )
    public void testSaveEmployeeStatusSuccessfully() throws InterruptedException {
        empStatusPage.fillEmployeeStatusForm(empStatus);
        Thread.sleep(3000);
        empStatusPage.clickSave();
        Thread.sleep(3000);
        By toastMessageLocator = By.cssSelector(
                "div.oxd-toast-container .oxd-toast--success .oxd-text--toast-message"
        );
        WebElement toastMessageElement = driver.findElement(toastMessageLocator);
        String toastMessage = toastMessageElement.getText();
        Assert.assertTrue(toastMessage.contains("Successfully Saved"), "Success message should be visible");
    }

    @Test(
            priority = 2,
            testName = "Test Case 02 : Duplicate employee status should show error"
    )
    public void testDuplicateEmployeeStatusError() throws InterruptedException {

        empStatusPage.fillEmployeeStatusForm(empStatus);
        empStatusPage.clickSave();
        Thread.sleep(3000);

        String error = empStatusPage.getFieldErrorMessageByLabel("Name");
        System.out.println(error);
        Assert.assertTrue(error.contains("Already exists"), "Duplicate status should show 'Already exists' error");
    }

    @Test(
            priority = 3,
            testName = "Test Case 03 : Validation error when name is empty"
    )
    public void testEmptyEmployeeStatusName() {
        empStatusPage.fillEmployeeStatusForm("");
        empStatusPage.clickSave();

        String error = empStatusPage.getFieldErrorMessageByLabel("Name");
        System.out.println(error);
        Assert.assertTrue(error.contains("Required"), "Empty name should show 'Required' error");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}