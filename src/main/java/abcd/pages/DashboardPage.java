package abcd.pages;

import abcd.utils.LocatorUtils;
import abcd.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object Model for Dashboard / Inventory Page.
 */
public class DashboardPage {

    private WebDriver driver;

    // Static Base Locators
    private By menuBtn = By.id("react-burger-menu-btn");
    private By shoppingCartIcon = By.className("shopping_cart_link");
    private By inventoryContainer = By.id("inventory_container");

    // Dynamic Locator String Templates for Reusability
    // e.g. //div[text()='%s']/ancestor::div[@class='inventory_item']//button
    private String dynamicAddToCartBtnXPath = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button";
    private String dynamicInputPlaceholderXPath = "//input[@placeholder='%s']";

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Checks if the inventory container is visible on the dashboard.
     */
    public boolean isDashboardLoaded() {
        WebElement container = WaitUtils.waitForVisibility(driver, inventoryContainer, 10);
        return container.isDisplayed();
    }

    /**
     * Clicks the menu button.
     */
    public void openMenu() {
        WaitUtils.waitForClickability(driver, menuBtn, 5).click();
    }

    /**
     * Adds an item to the cart using a dynamic XPath locator based on the item
     * name.
     * 
     * @param itemName Name of the item (e.g. "Sauce Labs Backpack")
     */
    public void addDynamicItemToCart(String itemName) {
        By addToCartLocator = LocatorUtils.getDynamicXPath(dynamicAddToCartBtnXPath, itemName);
        WebElement btn = WaitUtils.waitForClickability(driver, addToCartLocator, 10);
        btn.click();
    }

    /**
     * Example of using a dynamic placeholder locator.
     * 
     * @param placeholder
     * @param text
     */
    public void enterTextInDynamicInput(String placeholder, String text) {
        By inputLocator = LocatorUtils.getDynamicXPath(dynamicInputPlaceholderXPath, placeholder);
        WebElement input = WaitUtils.waitForVisibility(driver, inputLocator, 5);
        input.sendKeys(text);
    }
}
