package academy.teenfuture.crse.qa.hkbus.selenium;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import academy.teenfuture.crse.qa.hkbus.selenium.pages.HomePage;
import academy.teenfuture.crse.qa.hkbus.selenium.pages.NavBar;
import academy.teenfuture.crse.qa.hkbus.selenium.pages.RoutePage;
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
		super.configureBrowser("Chrome", true).get("https://hkbus.app/en");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		navBar = new NavBar(driver);

	}

	// TODO For debugging
	// @Test
	public void clickBlankAreaTest() throws InterruptedException {
		SearchPage searchPage = navBar.clickNavSearch();
		RoutePage routePage = searchPage.clickRouteCell(0);
		routePage.clickRouteStarBtn();
		Thread.sleep(2000);
		routePage.clickAddCollectionBtn();
		// routePage.clickCollectionBookmarkTogBtn(3);
		Thread.sleep(2000);
		routePage.clickBlankArea();
		Thread.sleep(2000);
		routePage.clickBlankArea();
		Thread.sleep(2000);
	}

	@Test
	public void addBusRoutesTest() throws InterruptedException {
		HomePage homePage;

		// Index of "Starred" in the home page tabs
		final int STARRED_HOME_TAB_INDEX = 2;

		// Select the route
		int repeat = 2;
		for (int i = 0; i < repeat; i++) {
			try {
				// TODO For debugging
				System.out.println("route: " + i);

				SearchBusRoute(i);
			} catch (ElementClickInterceptedException | NoSuchElementException e) {
				e.printStackTrace();
				// captureAndSaveScreenshot();
				Thread.sleep(1000);
				// Try to click blank area if ElementClickInterceptedException appears
				RoutePage routePage = new RoutePage(driver);
				routePage.clickBlankArea();
				Thread.sleep(1000);
				routePage.clickBlankArea();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				// captureAndSaveScreenshot();
			}
		}

		// Back to Home page
		// homePage = navBar.clickNavHome();
		// homePage.clickTab(STARRED_HOME_TAB_INDEX);
		// Thread.sleep(2000);
	}

	private void SearchBusRoute(int index) throws InterruptedException {
		SearchPage searchPage;
		RoutePage routePage;

		// Go to Search page
		searchPage = navBar.clickNavSearch();
		// Thread.sleep(2000);

		// Search route
		// searchPage.clickInAppKeyboardBtns(routeName, 0);
		// searchPage.clickInAppKeyboardBtn("1");
		// searchPage.clickInAppKeyboardBtn("0");
		// searchPage.clickInAppKeyboardBtn("M");
		// Thread.sleep(2000);

		// Go to Route page
		routePage = searchPage.clickRouteCell(index);
		addBusRoute(routePage);

		// Back to Search page
		searchPage = navBar.clickNavSearch();
		Thread.sleep(2000);
	}

	private void addBusRoute(RoutePage routePage) throws InterruptedException {
		// Index of "Starred" in the collection drawer list
		final int STARRED_COLLECTION_DRAWER_INDEX = 2;

		routePage.clickRouteStarBtn();
		routePage.clickCollectionBookmarkTogBtn(STARRED_COLLECTION_DRAWER_INDEX);
		// Thread.sleep(2000);
		routePage.clickCollectionOverlay();
		// Thread.sleep(2000);

		for (WebElement accordion : routePage.getStopAccordions()) {
			try {
				// Check if the accordion is expanded
				WebElement accordionSummary = accordion.findElements(By.xpath("./child::*")).get(0);

				// TODO For debugging
				// System.out.println(accordionSummary.getAttribute("outerHTML") + "\n");

				if (accordionSummary.getAttribute("aria-expanded").equals("false")) {
					accordion.click();
				}
				// Click favourite button (star) of the stop
				routePage.clickStopFavouriteBtn();
				// Thread.sleep(1000);
				routePage.clickCollectionBookmarkTogBtn(STARRED_COLLECTION_DRAWER_INDEX);
				routePage.clickCollectionOverlay();
			} catch (ElementClickInterceptedException | NoSuchElementException e) {
				e.printStackTrace();
				// captureAndSaveScreenshot();
				Thread.sleep(1000);
				// Try to click blank area if ElementClickInterceptedException appears
				routePage.clickBlankArea();
				Thread.sleep(1000);
				routePage.clickBlankArea();
				Thread.sleep(1000);
			}
		}
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
