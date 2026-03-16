package abcd.utils;

import org.openqa.selenium.By;

/**
 * Utility to generate dynamic locators at runtime.
 */
public class LocatorUtils {

    /**
     * Replaces a placeholder (%s) in an XPath or CSS selector string with a dynamic
     * value.
     * 
     * @param locatorTemplate The locator string containing '%s' e.g.
     *                        "//div[text()='%s']"
     * @param value           The dynamic value to substitute.
     * @return By locator
     */
    public static By getDynamicLocator(By baseLocator, String value) {
        String locatorType = baseLocator.toString();
        // Extract locator type and value
        String[] parts = locatorType.split(":", 2);
        String type = parts[0].trim();
        String locValue = parts[1].trim();

        // Format the string dynamically
        String finalLocValue = String.format(locValue, value);

        if (type.contains("By.xpath")) {
            return By.xpath(finalLocValue);
        } else if (type.contains("By.cssSelector")) {
            return By.cssSelector(finalLocValue);
        } else if (type.contains("By.id")) {
            return By.id(finalLocValue);
        }
        // fallback
        return By.xpath(finalLocValue);
    }

    /**
     * Utility for direct XPath string injection.
     * 
     * @param xpathTemplate //input[@placeholder='%s']
     * @param value         username
     * @return By.xpath
     */
    public static By getDynamicXPath(String xpathTemplate, String value) {
        return By.xpath(String.format(xpathTemplate, value));
    }
}
