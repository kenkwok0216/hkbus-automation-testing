package academy.teenfuture.crse.qa.hkbus.selenium;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import academy.teenfuture.crse.qa.hkbus.selenium.pages.HomePage;
import academy.teenfuture.crse.qa.hkbus.selenium.pages.NavBar;
import academy.teenfuture.crse.qa.hkbus.selenium.pages.RouteDetailPage;
import academy.teenfuture.crse.qa.hkbus.selenium.pages.SearchPage;
import academy.teenfuture.crse.qa.hkbus.selenium.pojo.BusRoute;
import academy.teenfuture.crse.qa.hkbus.selenium.util.readers.BusRouteDataReader;

public class AddBusRouteTest extends BaseTest {

    private static BusRouteDataReader busRouteReader;
    private static List<BusRoute> busRouteList;

    private NavBar navBar;

    @BeforeAll
    public static void startAll() {
        // Load the bus route testing data
        busRouteReader = new BusRouteDataReader();
        busRouteList = busRouteReader.readData();
    }

	@BeforeEach
	public void start() {
		super.configureBrowser("Chrome").get("https://hkbus.app/en");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        navBar = new NavBar(driver);
        
	}

    @Test
    public void addBusRoutesTest() throws InterruptedException {
        int repeat = 1;
        for (int i = 0; i < repeat; i++) {
            addBusRoute(busRouteList.get(i).getRoute());
        }
    }

    private void addBusRoute(String routeName) throws InterruptedException {
        SearchPage searchPage;
        HomePage homePage;
        RouteDetailPage routeDetailPage;

        // Go to Search page
        searchPage = navBar.clickNavSearch();
        Thread.sleep(2000);

        // searchPage.clickInAppKeyboardBtns(routeName, 0);
        // searchPage.clickInAppKeyboardBtn("1");
        // searchPage.clickInAppKeyboardBtn("0");
        // searchPage.clickInAppKeyboardBtn("M");
        // Thread.sleep(2000);

        routeDetailPage = searchPage.clickRouteCell(199);
        Thread.sleep(2000);

        // Back to Home page
        homePage = navBar.clickNavHome();
        Thread.sleep(2000);
    }

	@AfterEach
	public void end() {
		quitDriver();
	}

	@AfterAll
	public static void endAll() {
		endAllTest();
	}

}
