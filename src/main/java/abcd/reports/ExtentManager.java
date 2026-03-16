package abcd.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Manages ExtentReports instance for test reporting.
 */
public class ExtentManager {

    private static ExtentReports extentReports;

    public static ExtentReports createInstance() {
        if (extentReports == null) {
            String path = System.getProperty("user.dir") + "/target/ExtentReports/SparkReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);

            reporter.config().setReportName("Automation Execution Report");
            reporter.config().setDocumentTitle("Test Results");
            reporter.config().setTheme(Theme.STANDARD);

            extentReports = new ExtentReports();
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("Environment", "QA");
        }
        return extentReports;
    }
}
