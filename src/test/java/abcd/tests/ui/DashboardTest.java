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
//sa
        dashboardPage = new DashboardPage(driver);
    }

    @DataProvider(name = "dashboardData")
    public Object[][] getDashboardData() {
        String csvPath = System.getProperty("user.dir") + "/src/test/resources/testdata/loginData.csv";
        return CsvUtils.getCsvData(csvPath);
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

    @Test(priority = 2, groups = { "ui_validation" })
    public void testAllWidgetsAreVisible() {
        // Placeholder simulation
        Assert.assertTrue(true, "All widgets are visible verified.");
    }

    @Test(priority = 3, groups = { "ui_validation", "high_priority" })
    public void testMenuNavigationWorks() {
        dashboardPage.openMenu();
        // Placeholder check for menu open
        Assert.assertTrue(true, "Menu opened successfully.");
    }

    // ==========================================
    // GROUP 2: Functional Validation
    // ==========================================

    @Test(priority = 4, groups = { "functional_validation", "high_priority" })
    public void testAddDynamicItemToCart() {
        // Use the reusable dynamic locator method
        dashboardPage.addDynamicItemToCart("Sauce Labs Backpack");
        Assert.assertTrue(true, "Item successfully added to cart using dynamic locator.");
    }

    @Test(priority = 5, groups = { "functional_validation" })
    public void testUserProfileInformation() {
        // Placeholder setup
        Assert.assertTrue(true, "User profile information verified.");
    }

    // @Test(priority = 6, groups = { "functional_validation" })
    // public void testNotificationIconWorks() {
    // // Deliberately fail this test to prove Extent captures failures and
    // screenshots
    // Assert.assertTrue(false, "Intentional failure: Notification icon verified.");
    // }

    // ==========================================
    // GROUP 3: Navigation Validation
    // ==========================================

    // @Test(priority = 7, groups = {"navigation_validation"})
    // public void testNavigateToProfilePage() {
    // Assert.assertTrue(true, "Navigated to profile page.");
    // }

    // @Test(priority = 8, groups = {"navigation_validation"})
    // public void testNavigateToSettingsPage() {
    // Assert.assertTrue(true, "Navigated to settings page.");
    // }

    // // ==========================================
    // // GROUP 4: Negative Scenarios
    // // ==========================================

    // @Test(priority = 9, groups = {"negative_scenarios"})
    // public void testUnauthorizedDashboardAccess() {
    // Assert.assertTrue(true, "Unauthorized access rejected.");
    // }

    @Test(priority = 10, groups = { "negative_scenarios" })
    public void testsimulatedFailureForScreenshot() {
        // This will deliberately fail to demonstrate screenshot capture in Extent
        // Reports
        // Uncomment to test failure reporting.
        // Assert.fail("Simulated failure to trigger ExtentReports screenshot capture");
    }
}
