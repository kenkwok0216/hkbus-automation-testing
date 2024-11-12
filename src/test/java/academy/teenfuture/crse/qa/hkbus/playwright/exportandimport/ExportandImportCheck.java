package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.microsoft.playwright.Locator;

import academy.teenfuture.crse.qa.hkbus.playwright.BaseTest;
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
	@Order(1)
	public void normalTest() throws Exception {

		// Navigate to Setting Page
		page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a").click();
		Thread.sleep(1000);

		// Navigate to Customize page and customize settings
		page.locator("//div[@class='MuiBox-root hkbus-gg4vpm']//div[6]").click();
		Thread.sleep(1000);
		CustomizeSetting();
		page.press("body", "Escape");

		// Update Google Analytics settings
		Locator googleAnalytics = page.locator("//div[@class='MuiBox-root hkbus-gg4vpm']//div[10]");
		googleAnalytics.click();
		updateSetting(googleAnalytics, "Settings.Google Analytics");

		// Navigate to Search page
		page.locator("//*[@id=\"root\"]/div/div[3]/a[3]").click();
		Thread.sleep(1000);

		// Go to All page
		page.locator("(//button[@role='tab'])[2]").click();
		Thread.sleep(1000);

		// Perform actions on the first route
		handleFirstRoute();

		// Store ETAs
		storeItemInJson(page.locator("//*[@id=\"root\"]/div/div[2]/div/div[2]/div"), "Saved Etas");

		// Store Home Collections
		storeItemInJson(page.locator("(//button[@role='tab'])[4]"), "Collections Home");

		// Store Work Collections
		storeItemInJson(page.locator("(//button[@role='tab'])[5]"), "Collections Work");

		// Store New Collections
		storeItemInJson(page.locator("(//button[@role='tab'])[6]"), "Collections New");

		// Save Stops
		saveStopsInJson();

	}

	private void handleFirstRoute() throws Exception {
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

		for (int i = 3; i < allStopButtonCount; i++) {
			allStopButton.nth(i).dblclick();
			Thread.sleep(1000);
			page.locator("//html/body/div[3]/div[3]/div/h2/div[1]/button[1]").click();
			page.press("body", "Escape");

			allStopButton.nth(i).click();
			page.locator("//h6[text()='Collections']/following-sibling::button").click();
			addToAllCollections();
		}
	}

	private void storeItemInJson(Locator locator, String keyPrefix) throws Exception {
		locator.click();
		Thread.sleep(1000);

		Locator storedETAs = locator.locator("(//ul[@class='MuiList-root hkbus-1abambu']//li)");
		for (int i = 0; i < storedETAs.count(); i++) {
			String route = storedETAs.nth(i).locator("h2 > span").nth(0).innerText() + " ";
			String bus = storedETAs.nth(i).locator("h4").nth(0).innerText() + " ";
			String destination = storedETAs.nth(i).locator("h3 > b").innerText() + " ";
			String stops = storedETAs.nth(i).locator(".MuiListItemText-secondary").innerText() + " ";

			WriteJson.updateJsonFile(keyPrefix, route + bus + destination + stops);
		}
	}

	private void saveStopsInJson() throws Exception {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[2]").click();
		Thread.sleep(1000);
		Locator stopsNamesButtons = page.locator("//*[@id=\"root\"]/div/div[2]/div/div[1]").locator("button");

		for (int i = 0; i < stopsNamesButtons.count(); i++) {
			WriteJson.updateJsonFile("Saved Stops", stopsNamesButtons.nth(i).innerText());
		}
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