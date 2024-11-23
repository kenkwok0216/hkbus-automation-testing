package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.TimeoutError;

import academy.teenfuture.crse.qa.hkbus.playwright.BaseTest;
import academy.teenfuture.crse.qa.hkbus.playwright.exportandimport.util.ReadJson;
import academy.teenfuture.crse.qa.hkbus.playwright.exportandimport.util.WriteJson;

// We will try the import and export functions
// While it support the data store of the following data:
// 1. Saved Stops
// 2. Saved Etas
// 3. Collections
// 4. Customize Setting

//Default settings for import/export functions:
//- Appearance : "Same as system"
//- Power Saving Mode: "Map - On"
//- Platform Display Format: ① [may changed to ➊]
//- Refresh Interval: 30
//- Annotate Scheduled Bus: Off
//- Vibration: "On"
//- ETA Format: "Minute Remaining"
//- Number Pad Order: "123456789c0b"
//- Route Filtering: "Show all Route"
//- Bus Sort Order: "KMB first"
//- Google Analytics: "On"

// Therefore, to make sure those item will be import, we will adjust all of them
/**
 * This class contains methods for managing and testing settings, data storage,
 * and route handling in the HK Bus application.
 *
 * <p>
 * The methods in this class facilitate various operations including:
 * <ul>
 * <li>Customizing and verifying application settings.</li>
 * <li>Reading and storing links and items from the application.</li>
 * <li>Comparing current application data with stored values.</li>
 * <li>Handling route storage and manipulation.</li>
 * <li>Performing cleanup operations after test executions.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The class utilizes JSON files for storing configuration and collected data,
 * ensuring that tests can run in a clean state and maintain consistency across
 * test executions. The methods handle interactions with the application's UI
 * and manage data integrity by comparing current states with expected values
 * stored in JSON.
 * </p>
 *
 * <p>
 * This class is designed to be used within a testing framework, leveraging
 * annotations for lifecycle management (e.g., @AfterEach, @AfterAll) to ensure
 * that resources are properly managed and that tests do not interfere with each
 * other.
 * </p>
 *
 * @throws Exception if any errors occur during the execution of methods.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExportandImportCheck extends BaseTest {
	private static int addedStops = 0;
	// For this variable we have
	private final static String[] Name = { "stops collections", "home collections", "work collections",
			"new collections" };
	private static int[] addedcollections = { 0, 0, 0, 0 };

	/**
	 * Prepares the test environment before each test case. This method configures
	 * the browser to use Firefox, navigates to the HK Bus application, and copies
	 * the default JSON configuration file to a new location for testing.
	 *
	 * @throws InterruptedException if the thread is interrupted while executing
	 *                              this method.
	 */
	@BeforeEach
	public void start() throws InterruptedException {
		addedStops = 0;
		for (int i = 0; i < addedcollections.length; i++) {
			addedcollections[i] = 0;
		}

		super.configure("firefox").navigate("https://hkbus.app/en");
		// This is to handle the saved.json to store the edited item
		// Source file path
		Path sourcePath = Paths.get(System.getProperty("user.dir")
				+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/default.json");
		// Destination file path
		Path destinationPath = Paths.get(System.getProperty("user.dir")
				+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/saved.json");

		try {
			// Copy the file
			Files.copy(sourcePath, destinationPath);
			System.out.println("File copied successfully!");
		} catch (IOException e) {
			System.err.println("Error occurred while copying the file: " + e.getMessage());
		}

	}

	/**
	 * Executes a normal test case for data export and import functionality in the
	 * HK Bus application.
	 * 
	 * <p>
	 * This method performs the following steps:
	 * <ol>
	 * <li>Navigates to the settings page and customizes settings.</li>
	 * <li>Updates Google Analytics settings.</li>
	 * <li>Navigates to the search page and handles routes.</li>
	 * <li>Stores items and copies the export link.</li>
	 * <li>Clears user history and imports store data.</li>
	 * <li>Checks if the exported and imported data match expected values.</li>
	 * <li>Validates settings and customized values.</li>
	 * <li>Handles any errors encountered during the process, including copying
	 * error logs.</li>
	 * </ol>
	 * </p>
	 * 
	 * If all checks pass, the test case is marked as successful; otherwise, it logs
	 * the error details.
	 *
	 * @throws Exception if there are issues during the test execution, including
	 *                   data import errors.
	 */
	// This test will try the handle data import and export in a "normal" way
	@Test
	// @Disabled
	@Order(1)
	public void normalTest() throws Exception {
		String testName = "Normal test on data export and import";
		boolean error = false;

		// Navigate to Setting Page
		GoToSetting();

		// Navigate to Customize page and customize settings
		GoToCustomize();
		CustomizeSetting();
		page.press("body", "Escape");

		// Update Google Analytics settings
		Locator googleAnalytics = page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[10]");
		googleAnalytics.click();
		updateSetting(googleAnalytics, "Settings.Google Analytics");

		// Navigate to Search page
		GoToSearchPage();

		// Go to All tab
		GoToAllTab();

		// Perform actions on the first route
		handleRouteStore();

		// Navigate to Home Page
		GoToHomePage();

		try {
			storeItemProcess();
		} catch (Exception e) {
			super.generateExtentTest(testName, false, e.getMessage());
			error = true;
		}

		Thread.sleep(1000);

		// Then, we can go back to setting
		GoToSetting();

		// Then go to Data Export and copy the links
		String URL = CopyExportLink();

		WriteJson.updateJsonFile("Export URL", URL);

		// The the step is to clear user history
		ClearUserRecord();

		// At time point, data is set
		// Import the data
		try {

			ImportStoreData();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			error = true;
			super.generateExtentTest(testName, false,
					e.getMessage() + " and those data is stored in /exportandimporterror/error.json");
			Path sourcePath = Paths.get(System.getProperty("user.dir")
					+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/saved.json");
			Path destinationPath = Paths.get(System.getProperty("user.dir") + "/exportandimporterror/error.json");

			try {
				// Copy the file
				Files.copy(sourcePath, destinationPath);
				System.out.println("File copied successfully!");
			} catch (IOException e1) {
				System.err.println("Error occurred while copying the file: " + e1.getMessage());
			}
		}

		// Then we will start the Checking of data

		// The first step is check the numbers of import route first
		try {

			verifyImportedCollections();

		} catch (Exception e) {

			super.generateExtentTest(testName, false, e.getMessage(), page.screenshot());
			error = true;
			page.press("body", "Escape");
			Thread.sleep(2000);

		}

		// Then we will start the Checking of data
		GoToHomePage();

		// Check (Star)
		boolean testETAs = CompareItemInJson(page.locator("(//button[@role='tab'])[2]"), "Saved Etas");
		// Check Home Collections
		boolean testHome = CompareItemInJson(page.locator("(//button[@role='tab'])[4]"), "Collections Home");
		// Check Collection Work
		boolean testWork = CompareItemInJson(page.locator("(//button[@role='tab'])[5]"), "Collections Work");
		// Check Collection New
		boolean testNew = CompareItemInJson(page.locator("(//button[@role='tab'])[6]"), "Collections New");
		// Check Stops
		boolean testStops = CompareStopsInJson();

		try {
			// if there are somethings is false
			if (!(testETAs && testHome && testWork && testNew && testStops)) {
				throw new Exception("The stored data is not match the show data");
			}
		} catch (Exception e) {
			error = true;
			System.out.println(e.getMessage());
			super.generateExtentTest(testName, false,
					e.getMessage() + " and those data is stored in /exportandimporterror/error.json");
			Path sourcePath = Paths.get(System.getProperty("user.dir")
					+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/saved.json");
			Path destinationPath = Paths.get(System.getProperty("user.dir") + "/exportandimporterror/error.json");

			try {
				// Copy the file
				Files.copy(sourcePath, destinationPath);
				System.out.println("File copied successfully!");
			} catch (IOException e1) {
				System.err.println("Error occurred while copying the file: " + e1.getMessage());
			}

		}

		// Then, we go to Setting page to check
		GoToSetting();

		googleAnalytics = page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[10]");

		boolean testSetting1 = checkSetting(googleAnalytics, "Settings.Google Analytics");

		// Then check value in customize
		GoToCustomize();

		boolean testSetting2 = CustomizeSettingCheck();

		try {
			// if there are somethings is false
			if (!(testSetting1 && testSetting2)) {
				throw new Exception("There are error in Export and Import function");
			}
		} catch (Exception e) {
			error = true;
			System.out.println(e.getMessage());
			super.generateExtentTest(testName, false,
					e.getMessage() + " and those data is stored in /exportandimporterror/error.json");
			Path sourcePath = Paths.get(System.getProperty("user.dir")
					+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/saved.json");
			Path destinationPath = Paths.get(System.getProperty("user.dir") + "/exportandimporterror/error.json");

			try {
				// Copy the file
				Files.copy(sourcePath, destinationPath);
				System.out.println("File copied successfully!");
			} catch (IOException e1) {
				System.err.println("Error occurred while copying the file: " + e1.getMessage());
			}

		}

		if (!error) {
			super.generateExtentTest(testName, true, "This test case is passed");
		}

	}

	/**
	 * Executes an abnormal test case for attempting to put all routes and stops
	 * into collections within the HK Bus application.
	 *
	 * <p>
	 * This method performs the following steps:
	 * <ol>
	 * <li>Navigates to the search page and performs actions on routes.</li>
	 * <li>Stores the items and copies the export link.</li>
	 * <li>Clears user history and imports store data.</li>
	 * <li>Checks if the exported and imported data match expected values.</li>
	 * <li>Handles any errors encountered during the process, including copying
	 * error logs.</li>
	 * </ol>
	 * </p>
	 *
	 * If any checks fail, the method logs the error details.
	 *
	 * @throws Exception if there are issues during the test execution or data
	 *                   import process.
	 */
	@Test
	// @Disabled // Check this later
	public void abnormalTest() throws Exception {

		boolean error = false;
		String testName = "Trying to put all route and stops into collecions";

		// Navigate to Search page
		GoToSearchPage();

		// Go to All tab
		GoToAllTab();

		// Perform actions on the first route
		handleManyRouteStore();

		// Navigate to Home Page
		GoToHomePage();

		try {
			storeItemProcess();
		} catch (Exception e) {
			super.generateExtentTest(testName, false, e.getMessage());
			error = true;
		}

		Thread.sleep(1000);

		// Then, we can go back to setting
		GoToSetting();

		// Then go to Data Export and copy the links
		String URL = CopyExportLink();

		WriteJson.updateJsonFile("Export URL", URL);

		// The the step is to clear user history
		ClearUserRecord();

		// At time point, data is set
		// Import the data
		try {

			ImportStoreData();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			error = true;
			super.generateExtentTest(testName, false,
					e.getMessage() + " and those data is stored in /exportandimporterror/error.json");
			Path sourcePath = Paths.get(System.getProperty("user.dir")
					+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/saved.json");
			Path destinationPath = Paths.get(System.getProperty("user.dir") + "/exportandimporterror/error.json");

			try {
				// Copy the file
				Files.copy(sourcePath, destinationPath);
				System.out.println("File copied successfully!");
			} catch (IOException e1) {
				System.err.println("Error occurred while copying the file: " + e1.getMessage());
			}

			if (!error) {
				super.generateExtentTest(testName, true, "This test case is passed");
			}
		}

		// The first steps is to check the numbers of imported is correct
		// Then we will start the Checking of data
		try {

			verifyImportedCollections();

		} catch (Exception e) {

			super.generateExtentTest(testName, false, e.getMessage(), page.screenshot());
			error = true;
			page.press("body", "Escape");

		}

		// Then we check the item
		GoToHomePage();

		// Check (Star)
		boolean testETAs = CompareItemInJson(page.locator("(//button[@role='tab'])[2]"), "Saved Etas");
		// Check Home Collections
		boolean testHome = CompareItemInJson(page.locator("(//button[@role='tab'])[4]"), "Collections Home");
		// Check Collection Work
		boolean testWork = CompareItemInJson(page.locator("(//button[@role='tab'])[5]"), "Collections Work");
		// Check Collection New
		boolean testNew = CompareItemInJson(page.locator("(//button[@role='tab'])[6]"), "Collections New");
		// Check Stops
		boolean testStops = CompareStopsInJson();

		try {
			// if there are somethings is false
			if (!(testETAs && testHome && testWork && testNew && testStops)) {
				throw new Exception("There are error in Export and Import function");
			}
		} catch (Exception e) {
			error = true;
			System.out.println(e.getMessage());
			super.generateExtentTest(testName, false,
					e.getMessage() + " and those data is stored in /exportandimporterror/error.json");
			Path sourcePath = Paths.get(System.getProperty("user.dir")
					+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/saved.json");
			Path destinationPath = Paths.get(System.getProperty("user.dir") + "/exportandimporterror/error.json");

			try {
				// Copy the file
				Files.copy(sourcePath, destinationPath);
				System.out.println("File copied successfully!");
			} catch (IOException e1) {
				System.err.println("Error occurred while copying the file: " + e1.getMessage());
			}

		}

	}

	/**
	 * Verifies that the number of imported collections matches the expected value.
	 *
	 * <p>
	 * This method navigates to the settings and manages collections. It retrieves
	 * the list of collections and checks if the number of ETAs matches the expected
	 * count represented by the variable {@code addedcollections}. If there is a
	 * mismatch, an exception is thrown indicating the expected and actual values.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted during execution.
	 * @throws Exception            if the number of imported collections does not
	 *                              match the expected value.
	 */
	private void verifyImportedCollections() throws InterruptedException, Exception {
		GoToSetting();
		GoToCustomize();
		// This is the go to manage collections
		page.locator("//html/body/div[3]/div[3]/div/ul/div[1]").click();

		Thread.sleep(1000);
		// Locate the collectionsLists
		Locator collectionLists = page.locator("//html/body/div[3]/div[3]/div/div/div[2]");
		Locator dataStores = collectionLists.locator("span:has-text('Number of ETAs:')");

		List<String> errors = new ArrayList<>(); // To collect error messages
		for (int i = 0; i < dataStores.count(); i++) {
			String[] parts = dataStores.nth(i).innerText().split(": ");
			if (!parts[1].equals(String.valueOf(addedcollections[i]))) {
				errors.add("The import data in " + Name[i] + " is incorrect as the imported number should be "
						+ addedcollections[i] + " but now is " + parts[1]);
			}
		}

		// After the loop, check if there were any errors
		if (!errors.isEmpty()) {
			throw new Exception(String.join("<br><br>", errors)); // Throw an exception with all error messages
		}

		page.press("body", "Escape");
		Thread.sleep(1000);

	}

	/**
	 * Imports store data from a specified URL in the HK Bus application.
	 *
	 * <p>
	 * This method navigates to the settings, activates the data import feature,
	 * fills in the URL retrieved from the exported data, and attempts to import the
	 * data. It checks if the import button is enabled before proceeding.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 * @throws Exception            if the URL is incorrect or if any other error
	 *                              occurs during data import.
	 */
	private void ImportStoreData() throws InterruptedException, Exception {

		GoToSetting();
		// Click Data Import
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[8]").click();
		Locator inputField = page.locator("//input[@aria-invalid='false']");
		String exportUrl = (String) ReadJson.readJsonFile("Export URL", false);

		// This is to "activate" the accept button
		String lastChar = exportUrl.substring(exportUrl.length() - 1);
		String inputToFill = exportUrl.substring(0, exportUrl.length() - 1);
		inputField.focus();
		inputField.fill(inputToFill);
		Thread.sleep(500);
		inputField.press(lastChar);

		Locator acceptButtton = page.locator("(//button[@type='button'])[3]");
		if (acceptButtton.isDisabled()) {
			throw new Exception("The URL produced is incorrect");
		} else {
			acceptButtton.click();
		}

	}

	/**
	 * Navigates to the settings page in the HK Bus application.
	 *
	 * <p>
	 * This method simulates a click on the settings link and waits for the page to
	 * load.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	private void GoToSetting() throws InterruptedException {
		page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a").click();
		Thread.sleep(1000);
	}

	/**
	 * Navigates to the customize settings page in the HK Bus application.
	 *
	 * <p>
	 * This method simulates a click on the customize settings link and waits for
	 * the page to load.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	private void GoToCustomize() throws InterruptedException {
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		Thread.sleep(1000);
	}

	/**
	 * Navigates to the search page in the HK Bus application.
	 *
	 * <p>
	 * This method simulates a click on the search page link and waits for the page
	 * to load.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	private void GoToSearchPage() throws InterruptedException {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[3]").click();
		Thread.sleep(1000);
	}

	/**
	 * Navigates to the "All" tab in the search page of the HK Bus application.
	 *
	 * <p>
	 * This method simulates a click on the "All" tab button and waits for the tab
	 * content to load.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	private void GoToAllTab() throws InterruptedException {
		page.locator("(//button[@role='tab'])[2]").click();
		Thread.sleep(1000);
	}

	/**
	 * Navigates to the home page of the HK Bus application.
	 *
	 * <p>
	 * This method simulates a click on the home page link and waits for the page to
	 * load.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	private void GoToHomePage() throws InterruptedException {
		// Navigate to Home Page
		page.locator("//*[@id=\"root\"]/div/div[3]/a[1]").click();
		Thread.sleep(1000);
	}

	/**
	 * Copies the export link from the data export section of the HK Bus
	 * application.
	 *
	 * <p>
	 * This method navigates to the home page, clicks on the data export link, and
	 * retrieves the URL from the input field.
	 * </p>
	 *
	 * @return the URL of the export link as a String.
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	private String CopyExportLink() throws InterruptedException {
		// Navigate to Home Page
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[7]").click();
		Thread.sleep(1000);
		String URL = page.locator("//div[contains(@class,'MuiInputBase-root MuiOutlinedInput-root')]//input[1]")
				.getAttribute("value");
		return URL;
	}

	/**
	 * Clears the user records in the HK Bus application.
	 *
	 * <p>
	 * This method navigates to the settings and customize sections, confirms the
	 * deletion of user records, and refreshes the page to clear any cached data.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting.
	 */
	private void ClearUserRecord() throws InterruptedException {
		GoToSetting();
		GoToCustomize();

		// To click accept for confirm delete
		page.onceDialog(dialog -> {
			dialog.accept();
		});

		// Clear usage record here
		page.locator("//html/body/div[3]/div[3]/div/ul/div[13]").click();
		page.press("body", "Escape");
		// Refresh the page to reload all the time (i.e. clear cache)
		page.reload();
		Thread.sleep(1000);

	}

	/**
	 * Handles the storage of routes and stops into collections in the HK Bus
	 * application.
	 *
	 * <p>
	 * This method interacts with the first route, adds stops to the collection, and
	 * allows for the selection of multiple collections.
	 * </p>
	 *
	 * @throws Exception if any error occurs during the handling of routes and
	 *                   stops.
	 */
	private static void handleRouteStore() throws Exception {
		Locator allRoute = page.locator("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div[2]/div/div");
		Locator routeButtonLocator = allRoute.locator("button");
		routeButtonLocator.nth(0).click();
		Thread.sleep(1000);

		Locator allStop = page.locator("//*[@id=\"root\"]/div/div[2]/div[3]");
		Locator allStopButton = allStop.locator("role=button");
		int allStopButtonCount = allStopButton.count();

		// Interact with the first button
		allStopButton.nth(0).click();
		Thread.sleep(1000);
		Locator bookmarkicon = page.locator("//html/body/div[3]/div[3]/div/h2/div[1]/button[1]");
		if (bookmarkicon.locator("svg").getAttribute("data-testid").equals("BookmarkBorderIcon")) {
			bookmarkicon.click();
			addedStops++;
		}
		page.press("body", "Escape");

		allStopButton.nth(3).click();

		// press add button to add a new collection
		page.locator("//h6[text()='Collections']/following-sibling::button");
		// Thread.sleep(1000);
		// This is to add new collection
		page.locator("//div[@class='MuiBox-root hkbus-gg4vpm']//button[1]").click();
		page.press("body", "Escape");
		// To add to all collection
		addToAllCollections();
		page.press("body", "Escape");

		for (int i = 4; i < allStopButtonCount; i++) {
			allStopButton.nth(i).dblclick();
			// Thread.sleep(1000);
			allStopButton = allStop.locator("role=button");
			// access the bookmarked with full xpath
			bookmarkicon = page.locator("//html/body/div[3]/div[3]/div/h2/div[1]/button[1]");
			if (bookmarkicon.locator("svg").getAttribute("data-testid").equals("BookmarkBorderIcon")) {
				bookmarkicon.click();
				addedStops++;
			}
			page.press("body", "Escape");

			allStopButton.nth(i).click();
			// page.locator("//h6[text()='Collections']/following-sibling::button").click();
			addToAllCollections();
		}
	}

	/**
	 * Handles the storage of multiple routes and their stops into collections in
	 * the HK Bus application.
	 *
	 * <p>
	 * This method iterates over a list of links, navigating to each one, adds stops
	 * to collections, and manages the collection settings for each route.
	 * </p>
	 *
	 * @throws Exception if any error occurs during the handling of multiple routes
	 *                   and stops.
	 */
	private static void handleManyRouteStore() throws Exception {
		String[] links = ReadLink();
		for (int i = 0; i < links.length; i++) {
			try {
				page.navigate(links[i]);

				Thread.sleep(750);
				Locator allStop = page.locator("//*[@id=\"root\"]/div/div[2]/div[3]");
				Locator allStopButton = allStop.locator("role=button");
				int allStopButtonCount = allStopButton.count();
				System.out.println("iteration: " + i + " number of stops: " + allStopButtonCount);
				System.out.println("added stops: " + addedStops + " added Collections: " + addedcollections[0]);

				// Interact with the first button
				allStopButton.nth(0).click();
				Locator bookmarkicon = page.locator("//html/body/div[3]/div[3]/div/h2/div[1]/button[1]");
				if (bookmarkicon.locator("svg").getAttribute("data-testid").equals("BookmarkBorderIcon")) {
					bookmarkicon.click();
					addedStops++;
				}

				page.press("body", "Escape");

				allStopButton.nth(3).click(new Locator.ClickOptions().setTimeout(3000));

				if (i == 0) {
					// press add button to add a new collection
					page.locator("//h6[text()='Collections']/following-sibling::button");
					// Thread.sleep(1000);
					// This is to add new collection
					page.locator("//div[@class='MuiBox-root hkbus-gg4vpm']//button[1]").click();
					page.press("body", "Escape");
				}
				// To add to all collection
				addToAllCollections();

				for (int j = 4; j < allStopButtonCount; j++) {
					allStopButton.nth(j).dblclick();
					// Thread.sleep(1000);
					allStopButton = allStop.locator("role=button");
					// access the bookmarked with full xpath
					bookmarkicon = page.locator("//html/body/div[3]/div[3]/div/h2/div[1]/button[1]");
					if (bookmarkicon.locator("svg").getAttribute("data-testid").equals("BookmarkBorderIcon")) {
						bookmarkicon.click(new Locator.ClickOptions().setTimeout(3000));
						addedStops++;
					}

					page.press("body", "Escape");

					allStopButton.nth(j).click(new Locator.ClickOptions().setTimeout(3000));
					// page.locator("//h6[text()='Collections']/following-sibling::button").click();
					addToAllCollections();

				}

				if (i == 2) {
					break;
				}
			} catch (TimeoutError e) {
				System.out.println("Timeout occurred in iterations: " + i);
				System.out.println("iterate it again");
				i--;
				continue;
			}
		}

	}

	/**
	 * Reads a list of links from a JSON file.
	 *
	 * <p>
	 * This method reads the content of a JSON file containing links and returns an
	 * array of strings representing those links.
	 * </p>
	 *
	 * @return an array of links as strings.
	 * @throws IOException   if an I/O error occurs while reading the file.
	 * @throws JSONException if there is an error parsing the JSON content.
	 */
	private static String[] ReadLink() throws IOException, JSONException {
		String filePath = System.getProperty("user.dir") + "/ButtonLinks/links_20241114.json";
		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonObject = new JSONObject(content);
		JSONArray linksArray = jsonObject.getJSONArray("links");

		// Create a string array to hold the links
		String[] links = new String[linksArray.length()];

		for (int i = 0; i < linksArray.length(); i++) {
			links[i] = linksArray.getString(i);
		}
		return links;

	}

	/**
	 * Stores various collections of items in JSON format.
	 *
	 * <p>
	 * This method retrieves and stores data from the application's tabs, including
	 * ETAs, Home Collections, Work Collections, New Collections, and saves stops in
	 * JSON format.
	 * </p>
	 *
	 * @throws Exception if any error occurs during the storing process.
	 */
	private static void storeItemProcess() throws Exception {
		Thread.sleep(1000);
		List<String> errors = new ArrayList<>(); // To collect error messages

		// Store ETAs (Star)
		try {
			storeItemInJson(page.locator("(//button[@role='tab'])[2]"), "Saved Etas", 0);
		} catch (Exception e) {
			errors.add("Failed to store Saved Etas: " + e.getMessage());
		}
		Thread.sleep(1000);

		// Store Home Collections
		try {
			storeItemInJson(page.locator("(//button[@role='tab'])[4]"), "Collections Home", 1);
		} catch (Exception e) {
			errors.add("Failed to store Collections Home: " + e.getMessage());
		}
		Thread.sleep(1000);

		// Store Work Collections
		try {
			storeItemInJson(page.locator("(//button[@role='tab'])[5]"), "Collections Work", 2);
		} catch (Exception e) {
			errors.add("Failed to store Collections Work: " + e.getMessage());
		}
		Thread.sleep(1000);

		// Store New Collections
		try {
			storeItemInJson(page.locator("(//button[@role='tab'])[6]"), "Collections New", 3);
		} catch (Exception e) {
			errors.add("Failed to store Collections New: " + e.getMessage());
		}
		Thread.sleep(1000);

		// Save Stops
		try {
			saveStopsInJson();
		} catch (Exception e) {
			errors.add("Failed to save stops: " + e.getMessage());
		}
		Thread.sleep(1000);

		// After attempting to store all items, check for errors
		if (!errors.isEmpty()) {
			throw new Exception(String.join("<br><br>", errors)); // Throw an exception with all error messages
		}
	}

	/**
	 * Stores items from a specified UI locator into a JSON file.
	 *
	 * <p>
	 * This method interacts with the UI elements to retrieve data and updates the
	 * JSON file with the collected information, using a specified key prefix for
	 * organization.
	 * </p>
	 *
	 * @param locator   the locator for the UI element containing the items to
	 *                  store.
	 * 
	 * @param keyPrefix the prefix key used for storing the data in JSON.
	 * @param index     the index used to access the expected count of collections
	 *                  from the {@code addedcollections} array.
	 * @throws Exception if any error occurs during the storage process.
	 */
	private static void storeItemInJson(Locator locator, String keyPrefix, int index) throws Exception {
		locator.click();
		Thread.sleep(1000);

		Locator storedETAs = locator.locator("(//ul[@class='MuiList-root hkbus-1abambu']//li)");

		System.out.println(
				"item found: " + storedETAs.count() + " and item should be stored: " + addedcollections[index]);

		for (int i = 0; i < storedETAs.count(); i++) {
			String route = storedETAs.nth(i).locator("h2 > span").nth(0).innerText().trim() + " ";
			String bus = storedETAs.nth(i).locator("h4").nth(0).innerText().trim() + " ";
			String destination = storedETAs.nth(i).locator("h3 > b").innerText().trim() + " ";
			String stops = storedETAs.nth(i).locator(".MuiListItemText-secondary").innerText().trim();

			WriteJson.updateJsonFile(keyPrefix, route + bus + destination + stops);
		}

		if (storedETAs.count() < addedcollections[index]) {
			throw new Exception("There are limit on the show on stored collections as the limit is only "
					+ storedETAs.count() + " but there are " + addedcollections[index]);
		} else if (storedETAs.count() > addedcollections[index]) {
			throw new Exception("There are error on the show on stored collections");
		}

	}

	/**
	 * Compares items from a UI element with stored data in JSON format.
	 *
	 * <p>
	 * This method retrieves data from a specified UI element, compares it against
	 * stored data in a JSON file, and checks for consistency. Returns true if all
	 * items match; otherwise, returns false.
	 * </p>
	 *
	 * @param locator   the locator for the UI element containing the items to
	 *                  compare.
	 * @param keyPrefix the prefix key used to retrieve the stored data from JSON.
	 * @return true if all items match; false otherwise.
	 * @throws Exception if any error occurs during the comparison process.
	 */
	private boolean CompareItemInJson(Locator locator, String keyPrefix) throws Exception {
		locator.click();
		Thread.sleep(1000);

		Locator stored = locator.locator("(//ul[@class='MuiList-root hkbus-1abambu']//li)");

		// Read stored data from JSON and convert it to a List
		Object storedDataJson = ReadJson.readJsonFile(keyPrefix, false);
		JSONArray storedDataArray = (JSONArray) storedDataJson;

		// Create a Set to hold the JSON data for fast lookup
		Set<String> jsonSet = new HashSet<>();

		for (int j = 0; j < storedDataArray.length(); j++) {
			jsonSet.add(storedDataArray.getString(j).trim());
		}

		// Collect data from the UI elements
		for (int i = 0; i < stored.count(); i++) {
			String route = stored.nth(i).locator("h2 > span").nth(0).innerText().trim();
			String bus = stored.nth(i).locator("h4").nth(0).innerText().trim();
			String destination = stored.nth(i).locator("h3 > b").innerText().trim();
			String stops = stored.nth(i).locator(".MuiListItemText-secondary").innerText().trim();

			String getData = route + " " + bus + " " + destination + " " + stops;

			// Check if the collected UI data exists in the JSON data
			if (jsonSet.contains(getData)) {
				jsonSet.remove(getData); // Optional: Remove if you want to check for uniqueness
				// System.out.println(jsonSet.toString());
			}

		}

		// If no unmatched data remains, return true
		return jsonSet.isEmpty();

	}

	/**
	 * Saves the names of stops into a JSON file.
	 *
	 * <p>
	 * This method navigates to the stops section of the application and updates the
	 * JSON file with the names of the stops retrieved from the UI.
	 * </p>
	 *
	 * @throws Exception if any error occurs during the saving process.
	 */
	private static void saveStopsInJson() throws Exception {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[2]").click();
		Thread.sleep(1000);
		Locator stopsNamesButtons = page.locator("//*[@id=\"root\"]/div/div[2]/div/div[1]").locator("button");

		System.out
				.println("Stops founded: " + stopsNamesButtons.count() + " and Stops should be stored: " + addedStops);

		for (int i = 0; i < stopsNamesButtons.count(); i++) {
			WriteJson.updateJsonFile("Saved Stops", stopsNamesButtons.nth(i).innerText().trim());
		}

		if (stopsNamesButtons.count() < addedStops) {
			throw new Exception("There are limit on the show on stops as the limits is only " + stopsNamesButtons);
		} else if (stopsNamesButtons.count() > addedStops) {
			throw new Exception("There are error on the show on stops");
		}
	}

	/**
	 * Compares the names of stops from the UI with stored data in JSON format.
	 *
	 * <p>
	 * This method retrieves the names of stops displayed in the application and
	 * compares them against the stored data in a JSON file. Returns true if all
	 * stops match; otherwise, returns false.
	 * </p>
	 *
	 * @return true if all stops match; false otherwise.
	 * @throws Exception if any error occurs during the comparison process.
	 */
	private boolean CompareStopsInJson() throws Exception {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[2]").click();
		Thread.sleep(1000);
		Locator stopsNamesButtons = page.locator("//*[@id=\"root\"]/div/div[2]/div/div[1]").locator("button");

		// Read stored data from JSON and convert it to a List
		Object storedDataJson = ReadJson.readJsonFile("Saved Stops", false);
		JSONArray storedDataArray = (JSONArray) storedDataJson;

		// Create a Set to hold the JSON data for fast lookup
		Set<String> jsonSet = new HashSet<>();

		for (int j = 0; j < storedDataArray.length(); j++) {
			jsonSet.add(storedDataArray.getString(j).trim());
		}

		for (int i = 0; i < stopsNamesButtons.count(); i++) {
			String getData = stopsNamesButtons.nth(i).innerText().trim();

			// Check if the collected UI data exists in the JSON data
			if (jsonSet.contains(getData)) {
				jsonSet.remove(getData); // Optional: Remove if you want to check for uniqueness
				// System.out.println(jsonSet.toString());
			}
		}

		// If no unmatched data remains, return true
		return jsonSet.isEmpty();
	}

	/**
	 * Adds items to all available collections in the HK Bus application.
	 *
	 * <p>
	 * This method selects all checkboxes corresponding to different collections
	 * (ETAs, Home, Work, and New Collections) and closes the selection dialog.
	 * </p>
	 *
	 * @throws Exception if any error occurs during the adding process.
	 */
	private static void addToAllCollections() throws Exception {
		// clickCheckboxByIcon(page.locator("(//input[@type='checkbox'])[2]"));
		// clickCheckboxByIcon(page.locator("(//input[@type='checkbox'])[3]"));
		// clickCheckboxByIcon(page.locator("(//input[@type='checkbox'])[4]"));
		// clickCheckboxByIcon(page.locator("(//input[@type='checkbox'])[5]"));
		clickCheckboxByIcon(page.locator("//html/body/div[3]/div[3]/div/div[2]/div/div[2]/span"), 0);
		clickCheckboxByIcon(page.locator("//html/body/div[3]/div[3]/div/div[4]/div[1]/div[2]/span"), 1);
		clickCheckboxByIcon(page.locator("//html/body/div[3]/div[3]/div/div[4]/div[2]/div[2]/span"), 2);
		clickCheckboxByIcon(page.locator("//html/body/div[3]/div[3]/div/div[4]/div[3]/div[2]/span"), 3);
		page.press("body", "Escape");

	}

	/**
	 * Clicks a checkbox if it is represented by a specific icon.
	 *
	 * <p>
	 * This method checks if the checkbox icon matches "BookmarkBorderIcon" and
	 * clicks it if it does.
	 * </p>
	 *
	 * @param index   the index used to access the expected count of collections
	 *                from the {@code addedcollections} array.
	 * @param locator the locator for the checkbox element to interact with.
	 */
	private static void clickCheckboxByIcon(Locator locator, int index) {
		if (locator.locator("svg").getAttribute("data-testid").equals("BookmarkBorderIcon")) {
			locator.click(new Locator.ClickOptions().setTimeout(3000));
			addedcollections[index]++;
		}
	}

	/**
	 * Clicks a checkbox and increments a counter if it matches a specific icon.
	 *
	 * <p>
	 * This method checks if the checkbox icon matches "BookmarkBorderIcon", clicks
	 * it if it does, and increments the provided counter.
	 * </p>
	 *
	 * @param locator the locator for the checkbox element to interact with.
	 * @param number  an integer that represents a counter to track checkbox clicks.
	 */
	private void CustomizeSetting() throws Exception {
		// Appearance
		Locator appearance = page.locator("//html/body/div[3]/div[3]/div/ul/div[5]");
		updateSetting(appearance, "Settings.Appearance");

		// Power Saving Mode
		// Locator powerSavingMode =
		// page.locator("//html/body/div[3]/div[3]/div/ul/div[10]");
		// updateSetting(powerSavingMode, "Settings.Power Saving Mode");
		// No need to change power saving mode for convenience latter implementation

		// Platform Display Format
		Locator platformDisplayFormat = page.locator("//html/body/div[3]/div[3]/div/ul/div[9]");
		updateSetting(platformDisplayFormat, "Settings.Platform Display Format");

		// Refresh Interval
		Locator refreshInterval = page.locator("//html/body/div[3]/div[3]/div/ul/div[4]/div[2]/p/span");
		updateRefreshInterval(refreshInterval, "Settings.Refresh Interval");

		// Annotate Scheduled Bus
		Locator annotateScheduledBus = page.locator("//html/body/div[3]/div[3]/div/ul/div[8]");
		updateSetting(annotateScheduledBus, "Settings.Annotate Scheduled Bus");

		// Vibration
		Locator vibration = page.locator("//html/body/div[3]/div[3]/div/ul/div[12]");
		updateSetting(vibration, "Settings.Vibration");

		// ETA Format
		Locator etaFormat = page.locator("//html/body/div[3]/div[3]/div/ul/div[7]"); // Updated index if necessary
		updateSetting(etaFormat, "Settings.Eta Format");

		// Keyboard Layout
		Locator keyboardLayout = page.locator("//html/body/div[3]/div[3]/div/ul/div[6]"); // Updated index if necessary
		updateSetting(keyboardLayout, "Settings.Keyboard Layout");

		// Route Filtering
		Locator routeFiltering = page.locator("//html/body/div[3]/div[3]/div/ul/div[2]");
		updateSetting(routeFiltering, "Settings.Route Filtering");

		// Bus Sort Order
		Locator busSortOrder = page.locator("//html/body/div[3]/div[3]/div/ul/div[3]");
		updateSetting(busSortOrder, "Settings.Bus Sort Order");

	}

	/**
	 * Checks the current settings in the HK Bus application against stored values.
	 *
	 * <p>
	 * This method verifies that the current settings match the expected values
	 * stored in a JSON file. It checks various settings including appearance, power
	 * saving mode, platform display format, refresh interval, annotated scheduled
	 * bus, vibration, ETA format, keyboard layout, route filtering, and bus sort
	 * order.
	 * </p>
	 *
	 * @return true if all settings match the stored values; false otherwise.
	 * @throws Exception if any error occurs during the checking process.
	 */
	private boolean CustomizeSettingCheck() throws Exception {
		// Appearance
		Locator appearance = page.locator("//html/body/div[3]/div[3]/div/ul/div[5]");
		boolean test1 = checkSetting(appearance, "Settings.Appearance");

		// Power Saving Mode
		Locator powerSavingMode = page.locator("//html/body/div[3]/div[3]/div/ul/div[10]");
		boolean test2 = checkSetting(powerSavingMode, "Settings.Power Saving Mode");

		// Platform Display Format
		Locator platformDisplayFormat = page.locator("//html/body/div[3]/div[3]/div/ul/div[9]");
		boolean test3 = checkSetting(platformDisplayFormat, "Settings.Platform Display Format");

		// Refresh Interval
		Locator refreshInterval = page.locator("//html/body/div[3]/div[3]/div/ul/div[4]/div[2]/p/span");
		boolean test4 = checkRefreshInterval(refreshInterval, "Settings.Refresh Interval");

		// Annotate Scheduled Bus
		Locator annotateScheduledBus = page.locator("//html/body/div[3]/div[3]/div/ul/div[8]");
		boolean test5 = checkSetting(annotateScheduledBus, "Settings.Annotate Scheduled Bus");

		// Vibration
		Locator vibration = page.locator("//html/body/div[3]/div[3]/div/ul/div[12]");
		boolean test6 = checkSetting(vibration, "Settings.Vibration");

		// ETA Format
		Locator etaFormat = page.locator("//html/body/div[3]/div[3]/div/ul/div[7]"); // Updated index if necessary
		boolean test7 = checkSetting(etaFormat, "Settings.Eta Format");

		// Keyboard Layout
		Locator keyboardLayout = page.locator("//html/body/div[3]/div[3]/div/ul/div[6]"); // Updated index if necessary
		boolean test8 = checkSetting(keyboardLayout, "Settings.Keyboard Layout");

		// Route Filtering
		Locator routeFiltering = page.locator("//html/body/div[3]/div[3]/div/ul/div[2]");
		boolean test9 = checkSetting(routeFiltering, "Settings.Route Filtering");

		// Bus Sort Order
		Locator busSortOrder = page.locator("//html/body/div[3]/div[3]/div/ul/div[3]");
		boolean test10 = checkSetting(busSortOrder, "Settings.Bus Sort Order");

		return (test1 && test2 && test3 && test4 && test5 && test6 && test7 && test8 && test9 && test10);

	}

	/**
	 * Updates a specific setting in the HK Bus application and stores the new
	 * value.
	 *
	 * <p>
	 * This method clicks on the specified setting, retrieves its current value, and
	 * updates the corresponding entry in the JSON file.
	 * </p>
	 *
	 * @param locator the locator for the UI element representing the setting.
	 * @param keyPath the key used to store the setting's value in JSON.
	 * @throws Exception if any error occurs during the update process.
	 */
	// Method to handle clicking and updating settings, which only press is okay
	private static void updateSetting(Locator locator, String keyPath) throws Exception {
		// Click the setting
		locator.click();
		// Get the inner text of the clicked setting
		String value = locator.locator("p").innerText().trim();
		System.out.println(value);
		// Update the JSON file with the new value
		WriteJson.updateJsonFile(keyPath, value); // Change to false if using save.json
		Thread.sleep(1000);
	}

	/**
	 * Checks if the current setting value matches the stored value in JSON.
	 *
	 * <p>
	 * This method retrieves the current value of the specified setting and compares
	 * it with the value stored in the JSON file. It returns true if they match.
	 * </p>
	 *
	 * @param locator the locator for the UI element representing the setting.
	 * @param keyPath the key used to retrieve the setting's stored value from JSON.
	 * @return true if the current value matches the stored value; false otherwise.
	 * @throws Exception if any error occurs during the checking process.
	 */
	// Method to handle checking settings, which only press is okay
	private static boolean checkSetting(Locator locator, String keyPath) throws Exception {
		String value = locator.locator("p").innerText().trim();
		System.out.println("Get:" + value);

		String setting = (String) ReadJson.readJsonFile(keyPath, false);
		System.out.println("Saved: " + setting);

		System.out.println(keyPath + " : " + value.equals(setting));
		return (value.equals(setting));

	}

	/**
	 * Updates the refresh interval setting in the HK Bus application.
	 *
	 * <p>
	 * This method interacts with the refresh interval setting by clicking it and
	 * using keyboard input to navigate to the desired option. The updated value is
	 * then stored in the JSON file.
	 * </p>
	 *
	 * @param locator the locator for the UI element representing the refresh
	 *                interval.
	 * @param keyPath the key used to store the refresh interval's value in JSON.
	 * @throws Exception if any error occurs during the update process.
	 */
	// Special method for Refresh Interval that includes key presses
	private static void updateRefreshInterval(Locator locator, String keyPath) throws Exception {
		// Click the setting
		locator.click();
		// Press the right arrow key three times
		for (int i = 0; i < 3; i++) {
			locator.press("ArrowRight");
		}
		// Get the updated inner text
		String value = locator.innerText().trim();
		System.out.println(value);
		// Update the JSON file with the new value
		WriteJson.updateJsonFile(keyPath, value); // Change to false if using save.json
	}

	/**
	 * Checks if the current refresh interval value matches the stored value in
	 * JSON.
	 *
	 * <p>
	 * This method retrieves the current refresh interval value and compares it
	 * against the value stored in the JSON file. It returns true if they match.
	 * </p>
	 *
	 * @param locator the locator for the UI element representing the refresh
	 *                interval.
	 * @param keyPath the key used to retrieve the refresh interval's stored value
	 *                from JSON.
	 * @return true if the current value matches the stored value; false otherwise.
	 * @throws Exception if any error occurs during the checking process.
	 */
	// Special method for Refresh Interval that includes key presses
	private static boolean checkRefreshInterval(Locator locator, String keyPath) throws Exception {
		// Click the setting
		// locator.click();
		// Get the updated inner text
		String value = locator.innerText().trim();
		System.out.println("Get:" + value);

		String setting = (String) ReadJson.readJsonFile(keyPath, false);
		System.out.println("Saved: " + setting);

		System.out.println(keyPath + " : " + value.equals(setting));
		return (value.equals(setting));
	}

	/**
	 * Performs cleanup operations after each test case.
	 *
	 * <p>
	 * This method waits for a short period, calls the method to end the test, and
	 * deletes the saved JSON data file to ensure a clean state for subsequent
	 * tests.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while sleeping.
	 * @throws IOException          if an I/O error occurs during file deletion.
	 */
	@AfterEach
	public void endEach() throws InterruptedException {
		Thread.sleep(1000);
		endEachTest();
	}

	/**
	 * Performs cleanup operations after all test cases have been executed.
	 *
	 * <p>
	 * This method waits for a short period and calls the method to end all tests,
	 * ensuring that any necessary global cleanup is performed after all tests are
	 * complete.
	 * </p>
	 *
	 * @throws InterruptedException if the thread is interrupted while sleeping.
	 */
	@AfterAll
	public static void endAll() throws InterruptedException {
		Thread.sleep(1000);
		endAllTest();
	}

}