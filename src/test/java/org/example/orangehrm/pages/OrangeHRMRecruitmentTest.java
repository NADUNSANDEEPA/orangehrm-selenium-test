package org.example.orangehrm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Random;

import static org.example.orangehrm.pages.ConfigLink.ORANGE_HRM_LINK;

public class OrangeHRMRecruitmentTest {

    private WebDriver driver;
    private OrangeHRMLogin loginPage;
    private OrangeHRMRecruitmentPage recruitmentPage;
    private String randomVacancy;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new OrangeHRMLogin(driver);
        recruitmentPage = new OrangeHRMRecruitmentPage(driver);

        randomVacancy = "QA Vacancy " + new Random().nextInt(1000);
        System.out.println("Vacancy is "+randomVacancy);


        driver.get(ORANGE_HRM_LINK + "/web/index.php/auth/login");
        loginPage.enterUsername(ConfigLink.VALID_USERNAME);
        loginPage.enterPassword(ConfigLink.VALID_PASSWORD);
        loginPage.clickLoginButton();
    }

    @BeforeMethod
    public void login() {
        recruitmentPage.navigateToAddVacancy(ConfigLink.ORANGE_HRM_LINK+"/web/index.php/recruitment/addJobVacancy");
    }

    @Test(
            priority = 1,
            testName = "Test Case 01: All Fields Empty"
    )
    public void testAllFieldsEmpty() throws InterruptedException {
        Thread.sleep(2000);
        recruitmentPage.clickSave();

        Thread.sleep(2000);
        String vacancyError = recruitmentPage.getFieldErrorMessageByLabel("Vacancy Name");
        System.out.println("\nError for Vacancy Name: " + vacancyError);
        Assert.assertEquals(vacancyError, "Required");

        String jobTitleError = recruitmentPage.getFieldErrorMessageByLabel("Job Title");
        System.out.println("Error for Job Title: " + jobTitleError);
        Assert.assertEquals(jobTitleError, "Required");

        String managerError = recruitmentPage.getFieldErrorMessageByLabel("Hiring Manager");
        System.out.println("Error for Hiring Manager: " + managerError+"\n");
        Assert.assertEquals(jobTitleError, "Required");

    }

    @Test(
            priority = 2,
            testName = "Test Case 02: Successfully Save Vacancy"
    )
    public void testSaveVacancySuccessfully() throws InterruptedException {

        recruitmentPage.fillVacancyForm(
                randomVacancy, true, "Responsible for testing applications", true, "2"
        );

        recruitmentPage.clickSave();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/addJobVacancy"));
    }

    @Test(
            priority = 3,
            testName = "Test Case 03: Vacancy Already Exists"
    )
    public void testVacancyAlreadyExists() throws InterruptedException {

        recruitmentPage.fillVacancyForm(
                randomVacancy, true, "Responsible for testing applications", true, "2"
        );

        recruitmentPage.clickSave();

        String errorMessage = recruitmentPage.getDuplicateVacancyMessage();
        System.out.println(errorMessage);
        Assert.assertTrue(errorMessage.contains("Already exists"));
    }

    @Test(
            priority = 4,
            testName = "Test Case 04: Positions Should Be Numeric"
    )
    public void testPositionsNumericValidation() throws InterruptedException {

        recruitmentPage.fillVacancyForm(
                randomVacancy + "X", true, "Responsible for testing applications", true, "a"
        );

        recruitmentPage.clickSave();

        String numberOfPositionsError = recruitmentPage.getFieldErrorMessageByLabel("Number of Positions");
        System.out.println("Number of Positions: " + numberOfPositionsError);
        Assert.assertTrue(numberOfPositionsError.contains("Should be a numeric value"));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}