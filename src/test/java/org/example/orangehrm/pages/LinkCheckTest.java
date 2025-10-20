package org.example.orangehrm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.example.orangehrm.pages.ConfigLink.VALID_PASSWORD;
import static org.example.orangehrm.pages.ConfigLink.VALID_USERNAME;

public class LinkCheckTest {

    private WebDriver driver;
    private OrangeHRMLogin loginPage;
    private LinkCheck menuPage;


    private final List<String> mainMenuItems = Arrays.asList(
            "Admin",
            "PIM",
            "Leave",
            "Time",
            "Recruitment",
            "My Info",
            "Performance",
            "Dashboard",
            "Directory",
            "Maintenance",
            "Claim",
            "Buzz"
    );

    private int passCount = 0;
    private int failCount = 0;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        loginPage = new OrangeHRMLogin(driver);
        menuPage = new LinkCheck(driver);
    }

    @BeforeMethod
    public void login() {
        driver.get(ConfigLink.ORANGE_HRM_LINK+"/web/index.php/auth/login");
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLoginButton();
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"));
    }

    @Test
    public void testMenuNavigation() {
        int count = 0;
        for (String menu : mainMenuItems) {
            count ++;
            menuPage.clickMenuItem(menu);

            String currentUrl = menuPage.getCurrentUrl();
            if (menu.equals("My Info")) {
                Assert.assertTrue(currentUrl.contains("viewPersonalDetails"));
            } else {
                Assert.assertTrue(currentUrl.toLowerCase().contains(menu.toLowerCase().replaceAll("\\s", "")));
            }

            if (menu.equals("Maintenance")) {
                driver.navigate().to(ConfigLink.ORANGE_HRM_LINK+"/web/index.php/dashboard/index");
            }

            Assert.assertTrue(menuPage.pageHasNo404(), "Page contains 404 error: " + menu);
            System.out.println("Test Case "+count+" was passed. Link : "+menu);
            passCount++;
        }
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Total Passed: " + passCount);
        System.out.println("Total Failed: " + failCount);
        if (driver != null) driver.quit();
    }
}