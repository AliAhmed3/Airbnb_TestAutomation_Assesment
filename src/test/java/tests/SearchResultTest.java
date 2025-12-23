package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DateUtils;

public class SearchResultTest extends BaseTest {
    String location, checkIn, checkOut, expectedDateRange;
    int adults, children, checkInOffset, checkOutOffset, beds;

    @BeforeClass
    // Load JSON data and perform search.
    public void setupClass() {

        // Load JSON data once for the whole class
        location = testData.getJsonData("location");
        adults = Integer.parseInt(testData.getJsonData("adults"));
        children = Integer.parseInt(testData.getJsonData("children"));
        checkInOffset = Integer.parseInt(testData.getJsonData("checkInOffset"));
        checkIn = DateUtils.getAirbnbDate(checkInOffset);
        checkOutOffset = Integer.parseInt(testData.getJsonData("checkOutOffset"));
        checkOut = DateUtils.getAirbnbDate(checkOutOffset);
        expectedDateRange = DateUtils.getAirbnbDateRange(checkIn, checkOut);
        beds = Integer.parseInt(testData.getJsonData("beds"));

        homePage.performSearch(location, checkIn, checkOut, adults, children);
    }


    // ---------------------------------------------------
    // Verify search results match criteria
    // ---------------------------------------------------

    @Test(description = "Verify location in results header")
    public void verifyLocationInHeader() {
        searchResultsPage.popUpClose();

        Assert.assertTrue(
                searchResultsPage.getResultsHeaderText().contains("Rome"),
                "Location not found in search results header → Expected: "
                        + location + " | Actual: Rome city is not included"
        );
    }

    @Test(dependsOnMethods = "verifyLocationInHeader", description = "Verify location in filters bar")
    public void verifyLocationInFilters() {
        Assert.assertTrue(
                searchResultsPage.getHeaderLocation().contains(location.split(",")[0]),
                "Location not found in search results header → Expected: "
                        + location + " | Actual: Rome city is not included "
        );
    }

    @Test(dependsOnMethods = "verifyLocationInFilters", description = "Verify date range in filters")
    public void verifyDateRange() {

        Assert.assertTrue(searchResultsPage.getHeaderDates().contains(expectedDateRange),
                "Date range mismatch Expected: " + expectedDateRange +
                        " | Actual: " + searchResultsPage.getHeaderDates());
    }

    @Test(dependsOnMethods = "verifyDateRange", description = "Verify guests count in filters")
    public void verifyGuestsCount() {

        Assert.assertTrue(searchResultsPage.getHeaderGuests().contains((adults + children) + " guests"),
                "Guests count incorrect");
    }

    @Test(dependsOnMethods = "verifyGuestsCount", description = "Verify all listings have enough beds")
    public void verifyBedsCount() {

        Assert.assertTrue(
                searchResultsPage.allListingsHaveEnoughBeds(beds),
                "There is no card have the required number of beds or more"
        );
    }


}

