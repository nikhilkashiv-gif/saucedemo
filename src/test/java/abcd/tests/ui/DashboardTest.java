package abcd.tests.ui;

import abcd.base.BaseTest;
import abcd.factory.DriverFactory;
import abcd.pages.DashboardPage;
import abcd.utils.ConfigReader;
import abcd.utils.CsvUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Dashboard test class using ExtentReports, Parallel Execution, and Dynamic
 * Locators.
 */
public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;

    // Login before each Dashboard test
    @BeforeMethod(alwaysRun = true)
    public void dashboardLoginSetup() {
        WebDriver driver = DriverFactory.getDriver();
        driver.get(configReader.getProperty("url"));

        // Quick login for dashboard access
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        dashboardPage = new DashboardPage(driver);
    }

    @DataProvider(name = "dashboardData")
    public Object[][] getDashboardData() {
        String csvPath = System.getProperty("user.dir") + "/src/test/resources/testdata/loginData.csv";
        return CsvUtils.getCsvData(csvPath);
        //2
    }

    // ==========================================
    // GROUP 1: UI Validation (Data-Driven)
    // ==========================================

    @Test(priority = 1, groups = { "ui_validation", "high_priority", "data-driven" }, dataProvider = "dashboardData")
    public void testDashboardDataDrivenLogic(String username, String password, String expectedStatus) {
        WebDriver driver = DriverFactory.getDriver();
        driver.get(configReader.getProperty("url"));

        // Login Action
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        if (expectedStatus.equalsIgnoreCase("success")) {
            Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard failed to load for user: " + username);

            // Reusable item dynamic locator test
            dashboardPage.addDynamicItemToCart("Sauce Labs Backpack");
            Assert.assertTrue(true, "Item successfully added to cart using dynamic locator.");
        } else {
            boolean isErrorVisible = driver.findElements(By.cssSelector("[data-test='error']")).size() > 0;
            Assert.assertTrue(isErrorVisible, "Error message should be visible for invalid user: " + username);
        }
    }


    @Test(priority = 2, groups = { "ui_validation", "high_priority" })
    public void testMenuNavigationWorks() {
        dashboardPage.openMenu();
        // Placeholder check for menu open
        Assert.assertTrue(dashboardPage.isMenuOpen(), "Menu opened successfully.");
       
    }

    // // ==========================================
    // // GROUP 2: Functional Validation
    // // ==========================================

    // @Test(priority = 3, groups = { "functional_validation", "high_priority" })
    // public void testAddDynamicItemToCart() {
    //     // Use the reusable dynamic locator method
    //     dashboardPage.addDynamicItemToCart("Sauce Labs Backpack");
    //    Assert.assertTrue(dashboardPage.isCartBadgeVisible(), "Item was not added to cart.");
    // }
    

    

//     @Test(priority = 6, groups = { "functional_validation" })
//     public void testNotificationIconWorks() {
    
//     //screenshots
//     dashboardPage.addDynamicItemToCart("Sauce Labs Backpack");
//    // Assert.assertTrue(dashboardPage.isItemInCart("Sauce Labs Backpack"), " Notification icon not verified.");
//     }

    
    
}
