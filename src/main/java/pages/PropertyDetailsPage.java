package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JsActions;
import utils.WaitUtility;

import java.time.Duration;

public class PropertyDetailsPage {
    private final WebDriver driver;

    // =======================
    // Locators
    // =======================
    private final By newTabPopupBtn = By.cssSelector("button[aria-label='Close']");
    private final By whatThisPlaceOffers = By.xpath("//h2[contains(text(),'What this place offers')]");
    private final By showAmenities = By.xpath("//span[contains(text(), 'Show all') and contains(text(), 'amenities')]");
    private final By parkingAndFacilitiesSection = By.cssSelector("[aria-label='Parking and facilities']");
    private final By poolAmenity = By.cssSelector("div[id^='pdp_v3_parking_facilities_7_']");

    public PropertyDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    // =======================
    // Actions
    // =======================
    public PropertyDetailsPage switchToNewTab() {
        String currentWindow = driver.getWindowHandle();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > 1);

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
            }
        }
        return this;
    }

    public PropertyDetailsPage closeTheNewTabPopup() {
        try {
            // Add explicit wait for popup to be present
            WaitUtility.waitForElementVisible(driver, newTabPopupBtn);
            WaitUtility.clickingOnElement(driver, newTabPopupBtn);
            System.out.println("✔ popup closed");
            // Wait for popup to disappear
            WaitUtility.waitForElementToDisappear(driver, newTabPopupBtn);
        } catch (Exception e) {
            System.out.println("No popup found or failed to close: " + e.getMessage());
        }
        return this;
    }

    public PropertyDetailsPage scrollAndShowAllAmenities() {
        WaitUtility.waitForElementVisible(driver, whatThisPlaceOffers);
        JsActions.scrollToElementJS(driver, whatThisPlaceOffers);
        WaitUtility.clickingOnElement(driver, showAmenities);
        return this;
    }

    public void scrollToParkingAndFacilitiesSection() {
        WaitUtility.waitForElementVisible(driver, parkingAndFacilitiesSection);
        JsActions.scrollToElementJS(driver, parkingAndFacilitiesSection);
    }

    public String getPoolAmenityOption() {
        return WaitUtility.getText(driver, poolAmenity);
    }
}


