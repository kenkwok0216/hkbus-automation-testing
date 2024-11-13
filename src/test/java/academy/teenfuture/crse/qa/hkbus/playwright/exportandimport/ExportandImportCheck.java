package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.microsoft.playwright.Locator;

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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExportandImportCheck extends BaseTest {

	@BeforeEach
	public void start() throws InterruptedException {
		super.configure("firefox", false).navigate("https://hkbus.app/en");
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

	// This test will try the handle data import and export in a "normal" way
	@Test
	// @Disabled
	@Order(1)
	public void normalTest() throws Exception {
		String testName = "Normal test on data export and import";

//		// Navigate to Setting Page
//		GoToSetting();
//
//		// Navigate to Customize page and customize settings
//		GoToCustomize();
//		CustomizeSetting();
//		page.press("body", "Escape");
//
//		// Update Google Analytics settings
//		Locator googleAnalytics = page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[10]");
//		googleAnalytics.click();
//		updateSetting(googleAnalytics, "Settings.Google Analytics");
//
//		// Navigate to Search page
//		GoToSearchPage();
//
//		// Go to All tab
//		GoToAllTab();
//
//		// Perform actions on the first route
//		handleRouteStore();
//
//		// Navigate to Home Page
//		GoToHomePage();
//
//		// Store ETAs (Star)
//		storeItemInJson(page.locator("(//button[@role='tab'])[2]"), "Saved Etas");
//
//		// Store Home Collections
//		storeItemInJson(page.locator("(//button[@role='tab'])[4]"), "Collections Home");
//
//		// Store Work Collections
//		storeItemInJson(page.locator("(//button[@role='tab'])[5]"), "Collections Work");
//
//		// Store New Collections
//		storeItemInJson(page.locator("(//button[@role='tab'])[6]"), "Collections New");
//
//		// Save Stops
//		saveStopsInJson();
//
//		Thread.sleep(1000);
//
//		// Then, we can go back to setting
//		GoToSetting();
//
//		// Then go to Data Export and copy the links
//		String URL = CopyExportLink();
//
//		WriteJson.updateJsonFile("Export URL", URL);
//
//		// The the step is to clear user history
//		ClearUserRecord();

		// At time point, data is set
		// Import the data
		try {

			ImportStoreData();

		} catch (Exception e) {
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
				throw new Exception("There are error in Export and Import function");
			}
		} catch (Exception e) {
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

	private void GoToSetting() throws InterruptedException {
		page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a").click();
		Thread.sleep(1000);
	}

	private void GoToCustomize() throws InterruptedException {
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		Thread.sleep(1000);
	}

	private void GoToSearchPage() throws InterruptedException {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[3]").click();
		Thread.sleep(1000);
	}

	private void GoToAllTab() throws InterruptedException {
		page.locator("(//button[@role='tab'])[2]").click();
		Thread.sleep(1000);
	}

	private void GoToHomePage() throws InterruptedException {
		// Navigate to Home Page
		page.locator("//*[@id=\"root\"]/div/div[3]/a[1]").click();
		Thread.sleep(1000);
	}

	private String CopyExportLink() throws InterruptedException {
		// Navigate to Home Page
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[7]").click();
		Thread.sleep(1000);
		String URL = page.locator("//div[contains(@class,'MuiInputBase-root MuiOutlinedInput-root')]//input[1]")
				.getAttribute("value");
		return URL;
	}

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

	private void handleRouteStore() throws Exception {
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
		page.locator("//html/body/div[3]/div[3]/div/h2/div[1]/button[1]").click();
		page.press("body", "Escape");

		allStopButton.nth(3).click();

		// press add button to add a new collection
		page.locator("//h6[text()='Collections']/following-sibling::button");
		// Thread.sleep(1000);
		// This is to add new collection
		page.locator("//div[@class='MuiBox-root hkbus-gg4vpm']//button[1]").click();
		page.press("body", "Escape");
		// To add to all collection
		page.locator("(//input[@type='checkbox'])[2]").click(); // ETAs
		page.locator("(//input[@type='checkbox'])[3]").click(); // Home Collections
		page.locator("(//input[@type='checkbox'])[4]").click(); // Work Collections
		page.locator("(//input[@type='checkbox'])[5]").click(); // New Collections
		page.press("body", "Escape");

		for (int i = 4; i < allStopButtonCount; i++) {
			allStopButton.nth(i).dblclick();
			// Thread.sleep(1000);
			allStopButton = allStop.locator("role=button");
			// access the bookmarked with full xpath
			page.locator("//html/body/div[3]/div[3]/div/h2/div[1]/button[1]").click();
			page.press("body", "Escape");

			allStopButton.nth(i).click();
			// page.locator("//h6[text()='Collections']/following-sibling::button").click();
			addToAllCollections();
		}
	}

	private void storeItemInJson(Locator locator, String keyPrefix) throws Exception {
		locator.click();
		Thread.sleep(1000);

		Locator storedETAs = locator.locator("(//ul[@class='MuiList-root hkbus-1abambu']//li)");
		for (int i = 0; i < storedETAs.count(); i++) {
			String route = storedETAs.nth(i).locator("h2 > span").nth(0).innerText().trim() + " ";
			String bus = storedETAs.nth(i).locator("h4").nth(0).innerText().trim() + " ";
			String destination = storedETAs.nth(i).locator("h3 > b").innerText().trim() + " ";
			String stops = storedETAs.nth(i).locator(".MuiListItemText-secondary").innerText().trim();

			WriteJson.updateJsonFile(keyPrefix, route + bus + destination + stops);
		}
	}

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
				System.out.println(jsonSet.toString());
			}

		}

		// If no unmatched data remains, return true
		return jsonSet.isEmpty();

	}

	private void saveStopsInJson() throws Exception {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[2]").click();
		Thread.sleep(1000);
		Locator stopsNamesButtons = page.locator("//*[@id=\"root\"]/div/div[2]/div/div[1]").locator("button");

		for (int i = 0; i < stopsNamesButtons.count(); i++) {
			WriteJson.updateJsonFile("Saved Stops", stopsNamesButtons.nth(i).innerText().trim());
		}
	}

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
				System.out.println(jsonSet.toString());
			}
		}

		// If no unmatched data remains, return true
		return jsonSet.isEmpty();
	}

	private void addToAllCollections() throws Exception {
		page.locator("(//input[@type='checkbox'])[2]").click(); // ETAs
		page.locator("(//input[@type='checkbox'])[3]").click(); // Home Collections
		page.locator("(//input[@type='checkbox'])[4]").click(); // Work Collections
		page.locator("(//input[@type='checkbox'])[5]").click(); // New Collections
		page.press("body", "Escape");
	}

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

	// Method to handle clicking and updating settings, which only press is okay
	private static void updateSetting(Locator locator, String keyPath) throws Exception {
		// Click the setting
		locator.click();
		// Get the inner text of the clicked setting
		String value = locator.locator("p").innerText().trim();
		System.out.println(value);
		// Update the JSON file with the new value
		WriteJson.updateJsonFile(keyPath, value); // Change to false if using save.json
	}

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

	@AfterEach
	public void endEach() throws InterruptedException {
		Thread.sleep(1000);
		endEachTest();
	}

	@AfterAll
	public static void endAll() throws InterruptedException {
		Thread.sleep(1000);
		endAllTest();
	}

}