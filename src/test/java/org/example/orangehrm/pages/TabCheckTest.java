package org.example.orangehrm.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.example.orangehrm.pages.ConfigLink.*;

public class TabCheckTest {
    private WebDriver driver;
    private OrangeHRMLogin loginPage;
    private TabCheck tabLinkCheck;


    private final List<String> tabs = Arrays.asList(
            "Personal Details",
            "Contact Details",
            "Emergency Contacts",
            "Dependents",
            "Immigration",
            "Job",
            "Salary",
            "Report-to",
            "Qualifications",
            "Memberships"
    );

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        loginPage = new OrangeHRMLogin(driver);
        tabLinkCheck = new TabCheck(driver);
    }

    @BeforeMethod
    public void loginAndNavigate() {
        driver.get(ORANGE_HRM_LINK+"/web/index.php/auth/login");
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLoginButton();
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"));

        tabLinkCheck.navigateToPersonalDetails(ORANGE_HRM_LINK, 7);
    }

    @Test
    public void testTabsNavigation() throws InterruptedException {
        int count = 0;
        for (String tabName : tabs) {
            count ++;
            Thread.sleep(4000);
            tabLinkCheck.clickTab(tabName);

            String currentUrl = tabLinkCheck.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("empNumber/7"),
                    "Current URL should contain employee number: " + currentUrl);
            System.out.println("Tab Was Load : "+ count+" "+tabName);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}