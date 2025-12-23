package tests;

import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.HomePage;
import pages.PropertyDetailsPage;
import pages.SearchResultsPage;
import utils.JsonUtils;
import utils.PropertiesUtils;

public class BaseTest {
    protected JsonUtils testData;
    protected WebDriver driver;
    protected HomePage homePage;
    protected PropertyDetailsPage propertyDetailsPage;
    protected SearchResultsPage searchResultsPage;

    @BeforeClass
    public void setup() {
        testData = new JsonUtils("test-data");

        String baseUrl = PropertiesUtils.getPropertyValue("environment", "BASE_URL");
        driver = DriverFactory.getDriver();
        driver.get(baseUrl);

        // instantiate the Page Object so, Pages methods be accessible to all test methods that inherit from BaseTest
        homePage = new HomePage(driver);
        propertyDetailsPage = new PropertyDetailsPage(driver);
        searchResultsPage = new SearchResultsPage(driver);

    }

    @AfterClass
    public void closeBrowser() {
        DriverFactory.quitDriver();
    }
}