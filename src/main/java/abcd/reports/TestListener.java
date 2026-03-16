package abcd.reports;

import abcd.factory.DriverFactory;
import abcd.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener to track test lifecycle and capture ExtentReport
 * data/screenshots
 */
public class TestListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());

        // Extract priority and group/severity
        int priority = result.getMethod().getPriority();
        test.assignCategory("Priority-" + priority);

        String[] groups = result.getMethod().getGroups();
        for (String group : groups) {
            test.assignCategory(group);
        }

        // Add explicit info blocks to the report for Priority and Severity
        test.info("<b>Priority:</b> " + priority);
        test.info("<b>Severity/Group:</b> " + java.util.Arrays.toString(groups));

        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        extentTest.get().log(Status.FAIL, result.getThrowable());

        // Capture screenshot and attach to report
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            try {
                String base64Screenshot = ScreenshotUtils.getBase64Screenshot(driver);
                extentTest.get().addScreenCaptureFromBase64String(base64Screenshot, "Failure Screenshot");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // We defer flushing to onFinish(ISuite)
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extent != null) {
            extent.flush();
        }
        // Launch report automatically once per suite
        try {
            java.awt.Desktop.getDesktop()
                    .browse(new java.io.File(System.getProperty("user.dir") + "/target/ExtentReports/SparkReport.html")
                            .toURI());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
