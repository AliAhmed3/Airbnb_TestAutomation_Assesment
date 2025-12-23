package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtility;


public class HomePage {

    private final WebDriver driver;

    // =======================
    // Locators
    // =======================
    private final By popUpBtn = By.cssSelector("button[aria-label='Close']");
    private final By locationInput = By.id("bigsearch-query-location-input");
    private final By guestsButton = By.cssSelector("div[role='button']:nth-of-type(1)");
    private final By addAdultsButton = By.cssSelector("button[data-testid='stepper-adults-increase-button']");
    private final By addChildrenButton = By.cssSelector("button[data-testid='stepper-children-increase-button']");
    private final By searchButton = By.cssSelector("button[data-testid='structured-search-input-search-button']");
    private final By firstLocationSuggestion = By.id("bigsearch-query-location-suggestion-0");


    /* Constructor */
    public HomePage(WebDriver driver) {
        this.driver = driver;

    }

    // =======================
    // Actions
    // =======================
    public HomePage popUpClose() {
        try {
            WaitUtility.clickingOnElementWithGivenWait(driver, popUpBtn, 2);
            System.out.println("✔ popup closed");
        } catch (Exception ignored) {
            System.out.println("No popup found ");
        }
        return this;
    }

    public HomePage enterLocation(String location) {
        WaitUtility.clickingOnElement(driver, locationInput);
        WaitUtility.sendData(driver, locationInput, location);

        // Select the first suggestion
        WaitUtility.clickingOnElement(driver, firstLocationSuggestion);
        return this;
    }

    private By getSelectDateLocator(String date) {
        return By.cssSelector("[data-state--date-string='" + date + "']");
    }

    public HomePage selectCheckInDate(String date) {

        WaitUtility.clickingOnElement(driver, getSelectDateLocator(date));
        return this;
    }

    public HomePage selectCheckOutDate(String date) {
        WaitUtility.clickingOnElement(driver, getSelectDateLocator(date));
        return this;

    }

    public HomePage openGuestsMenu() {
        WaitUtility.clickingOnElement(driver, guestsButton);
        return this;
    }

    public HomePage addAdults(int count) {
        for (int i = 0; i < count; i++) {
            WaitUtility.clickingOnElement(driver, addAdultsButton);
        }
        return this;
    }

    public HomePage addChildren(int count) {
        for (int i = 0; i < count; i++) {
            WaitUtility.clickingOnElement(driver, addChildrenButton);
        }
        return this;
    }

    public void clickSearch() {
        WaitUtility.clickingOnElement(driver, searchButton);
    }

    public void performSearch(String location, String checkIn, String checkOut, int adults, int children) {
        this.popUpClose()
                .enterLocation(location)
                .selectCheckInDate(checkIn)
                .selectCheckOutDate(checkOut)
                .openGuestsMenu()
                .addAdults(adults)
                .addChildren(children)
                .clickSearch();
    }
}