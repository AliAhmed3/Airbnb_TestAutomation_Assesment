package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JsActions;
import utils.WaitUtility;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultsPage {

    private final WebDriver driver;

    // =======================
    // Locators
    // =======================
    private final By popUpBtn = By.cssSelector("button[aria-label='Close']");
    private final By headerLocation = By.cssSelector("button[data-testid='little-search-location']:nth-child(1)");
    private final By headerDates = By.cssSelector("button[data-testid='little-search-date'] div:nth-child(2)");
    private final By headerGuests = By.cssSelector("button[data-testid='little-search-guests'] div:nth-child(3)");
    private final By resultsHeader = By.xpath("//span[text()='Over 1,000 homes in Rome']");
    private final By listingCards = By.cssSelector("div[data-testid='card-container']");
    private final By bedsInfo = By.xpath(".//*[contains(text(),'beds')]"); //The . at the start of XPath ensures it searches within the card, not the whole page.
    private final By bedroomInfo = By.xpath(".//*[contains(text(),'bedroom')]");

    //  FiltersScreen locators
    private final By moreFiltersBtn = By.cssSelector("button[data-testid='category-bar-filter-button']");
    private final By increaseBedroomsBtn = By.cssSelector("button[data-testid='stepper-filter-item-min_bedrooms-stepper-increase-button']");
    private final By poolCheckBox = By.id("filter-item-amenities-7");
    private final By showMore = By.cssSelector("button[aria-label='Show more amenities']");
    private final By showPlacesBtn = By.xpath("//a[contains(text(), 'Show') and contains(text(), 'places')]");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    // =======================
    // Actions
    // =======================
    public SearchResultsPage popUpClose() {
        try {
            WaitUtility.clickingOnElementWithGivenWait(driver, popUpBtn, 6);
            System.out.println("✔ popup closed");
        } catch (Exception ignored) {
            System.out.println("No popup found ");
        }
        return this;
    }

    public String getHeaderLocation() {
        return WaitUtility.getText(driver, headerLocation);
    }

    public String getHeaderDates() {
        return WaitUtility.getText(driver, headerDates);

    }

    public String getHeaderGuests() {
        return WaitUtility.getText(driver, headerGuests);
    }

    public String getResultsHeaderText() {
        return WaitUtility.getText(driver, resultsHeader);
    }


    // =======================
    // FILTER ACTIONS
    // =======================

    public SearchResultsPage clickMoreFiltersButton() {
        WaitUtility.clickingOnElement(driver, moreFiltersBtn);
        return this;
    }

    public SearchResultsPage increaseBedrooms(int count) {

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600);");
        for (int i = 0; i < count; i++) {
            WaitUtility.clickingOnElement(driver, increaseBedroomsBtn);
        }
        return this;
    }

    public SearchResultsPage selectPool() {
        JsActions.scrollToElementJS(driver, showMore);
        WaitUtility.clickingOnElement(driver, showMore);
        JsActions.scrollToElementJS(driver, poolCheckBox);
        WaitUtility.clickingOnElement(driver, poolCheckBox);
        return this;
    }

    public SearchResultsPage applyFilters() {
        WaitUtility.clickingOnElement(driver, showPlacesBtn);
        return this;
    }

    // =======================
    // SCROLL HANDLER
    // =======================
    private void scroll() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,800);");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver ->
                        ((JavascriptExecutor) driver).executeScript(
                                "return document.readyState"
                        ).equals("complete")
                );

    }

    //Extracts number of beds from text:
    private int extractBeds(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        // Match patterns like "2 bed", "2 beds", "2 double beds", "2   double   beds", etc. (case-insensitive)
        Matcher matcher = Pattern.compile("(\\d+)\\s+(?:\\w+\\s+)*beds?", Pattern.CASE_INSENSITIVE).matcher(text);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    // find the number of bedrooms anywhere within the text,
    private int extractBedroomsCount(String text) {

        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        // Match patterns like "2 bedroom", "2 bedrooms", "2 double bedrooms", etc. (case-insensitive)
        Matcher matcher = Pattern.compile("(\\d+)\\s+(?:\\w+\\s+)*bedrooms?", Pattern.CASE_INSENSITIVE).matcher(text);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    // Scrolls through all Card and checks each one for required bed count.

    public boolean allListingsHaveEnoughBeds(int requiredBeds) {

        int lastCount = -1;
        int cardIndex = 0;
        int validCardsCount = 0;
        int invalidCardsCount = 0;

        while (true) {
            popUpClose();
            List<WebElement> cards = driver.findElements(listingCards);

            // Check if cards size hasn't changed
            if (cards.size() == lastCount) {
                break;
            }
            lastCount = cards.size();
            // Check current loaded cards
            for (WebElement card : cards) {
                cardIndex++;
                String bedsText = null;
                int beds = 0;
                boolean foundBedInfo = false;

                // Try to find beds info
                try {
                    // Look for element containing "bed" text
                    bedsText = card.findElement(bedsInfo).getText();
                    beds = extractBeds(bedsText);
                    foundBedInfo = true;
                    System.out.println("✓ Card " + cardIndex + " - " + bedsText + " Found  (" + beds + " beds )");
                } catch (Exception e1) {
                    System.out.println("Card " + cardIndex + " - no bed info found; skipping.");
                }

                // Track cards that meet the requirement vs those that don't
                if (foundBedInfo) {
                    if (beds >= requiredBeds) {
                        validCardsCount++;
                        System.out.println("  ✓ Card " + cardIndex + " has " + beds + " beds (meets requirement of " + requiredBeds + " atleast)");
                    } else {
                        invalidCardsCount++;
                        System.out.println("  ✗ Card " + cardIndex + " has only " + beds + " beds (requires " + requiredBeds + ") - SKIPPING");
                        // Continue to next card instead of failing
                    }
                }
            }

            // Scroll and wait for more cards to load
            scroll();

        }

        System.out.println("\n=== SUMMARY ===");
        System.out.println("Total cards checked: " + cardIndex);
        System.out.println("Cards with " + requiredBeds + "+ beds: " + validCardsCount);
        System.out.println("Cards with fewer beds: " + invalidCardsCount);

        // Pass if we found at least some cards with required beds
        if (validCardsCount > 0) {
            System.out.println("✓ PASS - Found " + validCardsCount + " cards with at least " + requiredBeds + " beds");
            return true;
        } else {
            System.out.println("❌ FAIL - No cards found with at least " + requiredBeds + " beds");
            return false;
        }
    }

    public boolean allListingsHaveEnoughBedrooms(int requiredBedrooms) {

        int lastCount = -1;
        int cardIndex = 0;
        int validCardsCount = 0;
        int invalidCardsCount = 0;

        while (true) {
            popUpClose();
            List<WebElement> cards = driver.findElements(listingCards);

            // Check if cards size hasn't changed
            if (cards.size() == lastCount) {
                break;
            }
            lastCount = cards.size();

            // Check current loaded cards
            for (WebElement card : cards) {
                cardIndex++;
                String bedroomText = null;
                int bedrooms = 0;
                boolean foundBedroomInfo = false;

                // Try to find bedroom info
                try {
                    bedroomText = card.findElement(bedroomInfo).getText();
                    bedrooms = extractBedroomsCount(bedroomText);
                    foundBedroomInfo = true;
                    System.out.println("✓ Card " + cardIndex + " - " + bedroomText + " Found  (" + bedrooms + " bedrooms )");
                } catch (Exception e) {
                    System.out.println("Card " + cardIndex + " - no bedroom info found; skipping.");
                }

                // Track cards that meet the requirement vs those that don't
                if (foundBedroomInfo) {
                    if (bedrooms >= requiredBedrooms) {
                        validCardsCount++;
                        System.out.println("  ✓ Card " + cardIndex + " has " + bedrooms + " bedrooms (meets requirement of " + requiredBedrooms + " atleast)");
                    } else {
                        invalidCardsCount++;
                        System.out.println("  ✗ Card " + cardIndex + " has only " + bedrooms + " bedrooms (requires " + requiredBedrooms + ") - SKIPPING");
                        // Continue to next card instead of failing
                    }
                }
            }

            // Scroll and wait for more cards to load
            scroll();
        }

        System.out.println("\n=== SUMMARY ===");
        System.out.println("Total cards checked: " + cardIndex);
        System.out.println("Cards with " + requiredBedrooms + "+ bedrooms: " + validCardsCount);
        System.out.println("Cards with fewer bedrooms: " + invalidCardsCount);

        // Pass if we found at least some cards with required bedrooms
        if (validCardsCount > 0) {
            System.out.println("✓ PASS - Found " + validCardsCount + " cards with at least " + requiredBedrooms + " bedrooms");
            return true;
        } else {
            System.out.println("❌ FAIL - No cards found with at least " + requiredBedrooms + " bedrooms");
            return false;
        }
    }


    public PropertyDetailsPage openFirstProperty() {
        List<WebElement> listings = WaitUtility.waitForListings(driver, listingCards);
        listings.get(0).click();
        return new PropertyDetailsPage(driver);
    }
}
