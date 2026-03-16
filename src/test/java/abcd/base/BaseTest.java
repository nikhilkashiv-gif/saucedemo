package abcd.base;

import abcd.factory.DriverFactory;
import abcd.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base test class to be extended by all test files.
 */
public class BaseTest {

    protected ConfigReader configReader;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        configReader = new ConfigReader();
        String browser = configReader.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        // Factory automatically assigns thread-local driver instance
        WebDriver driver = DriverFactory.initDriver(browser);

        // Optionally navigate to default URL automatically
        // driver.get(configReader.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
