package org.example.orangehrm.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Random;

import static org.example.orangehrm.pages.ConfigLink.ORANGE_HRM_LINK;

public class OrangeHRMJobTitleTest {

    private WebDriver driver;
    private OrangeHRMLogin loginPage;
    private OrangeHRMJobTitlePage jobPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new OrangeHRMLogin(driver);
        jobPage = new OrangeHRMJobTitlePage(driver);

        driver.get(ORANGE_HRM_LINK + "/web/index.php/auth/login");
        loginPage.enterUsername(ConfigLink.VALID_USERNAME);
        loginPage.enterPassword(ConfigLink.VALID_PASSWORD);
        loginPage.clickLoginButton();
    }

    @BeforeMethod
    public void loginAndNavigate() {
        driver.get(ORANGE_HRM_LINK + "/web/index.php/admin/saveJobTitle");
    }

    @Test(description = "Add a new Job Title successfully")
    public void testAddNewJobTitleSuccessfully() throws InterruptedException {
        String title = "Automation Engineer - Grade " + new Random().nextInt(1000);

        jobPage.fillJobForm(
                title,
                "Responsible for automation testing",
                "D:\\project\\QA\\cypress-test\\cypress\\fixtures\\files\\sample-spec.pdf",
                "Test note for job title"
        );

        jobPage.clickSave();

        Thread.sleep(2000);
        By toastMessageLocator = By.cssSelector(
                "div.oxd-toast-container .oxd-toast--success .oxd-text--toast-message"
        );
        WebElement toastMessageElement = driver.findElement(toastMessageLocator);
        String toastMessage = toastMessageElement.getText();
        Assert.assertTrue(toastMessage.contains("Successfully Saved"), "Success message should be visible");
    }

    @Test(description = "Validate error when required fields are empty")
    public void testValidationErrorForEmptyRequiredFields() throws InterruptedException {

        jobPage.fillJobForm(
                "",
                "Responsible for automation testing",
                "D:\\project\\QA\\cypress-test\\cypress\\fixtures\\files\\sample-spec.pdf",
                "Test note for job title"
        );

        jobPage.clickSave();

        String error = jobPage.getRequiredErrorMessage();
        Assert.assertTrue(error.contains("Required"), "Required field error should be visible");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
