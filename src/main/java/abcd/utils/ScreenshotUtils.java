package abcd.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Utility for capturing screenshots.
 */
public class ScreenshotUtils {

    /**
     * Captures a screenshot and returns it as a Base64 string for Extent Reports.
     */
    public static String getBase64Screenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
