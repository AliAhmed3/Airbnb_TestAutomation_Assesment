package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.DateUtils;


public class ExtraFilterTest extends BaseTest {

    String location, checkIn, checkOut;
    int adults, children, checkInOffset, checkOutOffset, bedrooms;

    @BeforeClass
    // Load JSON data, perform search and apply extra filters.
    public void setupClass() {

        // Load JSON data once for the whole class
        location = testData.getJsonData("location");
        adults = Integer.parseInt(testData.getJsonData("adults"));
        children = Integer.parseInt(testData.getJsonData("children"));
        checkInOffset = Integer.parseInt(testData.getJsonData("checkInOffset"));
        checkIn = DateUtils.getAirbnbDate(checkInOffset);
        checkOutOffset = Integer.parseInt(testData.getJsonData("checkOutOffset"));
        checkOut = DateUtils.getAirbnbDate(checkOutOffset);
        bedrooms = Integer.parseInt(testData.getJsonData("bedrooms"));
        new HomePage(driver)
                .performSearch(location, checkIn, checkOut, adults, children);
        searchResultsPage.popUpClose()
                .clickMoreFiltersButton()
                .increaseBedrooms(bedrooms)
                .selectPool()
                .applyFilters()
                .popUpClose();
    }

    // ---------------------------------------------------
    // Verify results and details match extra filters
    // ---------------------------------------------------

    @Test(description = "VERIFY all results have at least the number of selected bedrooms.")
    public void verifyTheSelectedNumberOfBedrooms() {

        Assert.assertTrue(
                searchResultsPage.allListingsHaveEnoughBedrooms(bedrooms),
                "There is no card have the required number of beds or more"
        );
    }

    @Test(description = "VERIFY that pool option is displayed in the first property card under the ‘Facilities’ category.", dependsOnMethods = "verifyTheSelectedNumberOfBedrooms")
    public void VerifyThePoolOption() {
        searchResultsPage.openFirstProperty()
                .switchToNewTab()
                .closeTheNewTabPopup()
                .scrollAndShowAllAmenities()
                .scrollToParkingAndFacilitiesSection();

        Assert.assertTrue(propertyDetailsPage.getPoolAmenityOption().toLowerCase().contains("pool"),
                "Pool amenity was NOT found in the property details!");
    }

}

