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

		// First we will edit the customize Setting First (i.e. the 4th items)
		// Navigate to Setting Page first
		Locator SettingPage = page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a");
		SettingPage.click();
		Thread.sleep(1000);
		// The in those default setting, except from Google Analytics, all other is from
		// Customize page
		// To do customize page first, navigate to customize page
		Locator Customize = page.locator("//*[@id='root']/div/div[2]/div/ul/div[6]");
		Customize.click();
		Thread.sleep(1000);

		// This will set those setting in customize page
		// CustomizeSetting();

		// To close the Customize page
		page.press("body", "Escape");
		Thread.sleep(1000);

		// Then for Google Analytics
		Locator GoogleAnalytics = page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[10]");
		GoogleAnalytics.click();
		updateSetting(GoogleAnalytics, "Settings.Google Analytics");

		// After that we will try Saved Stops
		// Therefore, we go to Search page
		Locator Search = page.locator("//*[@id=\"root\"]/div/div[3]/a[3]");
		Search.click();
		Thread.sleep(1000);
		Locator All = page.locator("(//button[@role='tab'])[2]");
		Thread.sleep(1000);

		Locator AllRoute = page.locator("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div[2]/div/div");
		int AllButtonCount = AllRoute.locator("button").count();

		// The find the button "that shown in the screen"
		Locator RouteButtonLocator = AllRoute.locator("button");

		// For testing, we only check the first route
		RouteButtonLocator.nth(0).click();

		System.out.println(AllButtonCount);

		Thread.sleep(1000);
		Locator AllStop = page.locator("//*[@id=\"root\"]/div/div[2]/div[3]");
		int AllStopButtonCount = AllStop.locator("role=button").count();

		// for all case nth(1,2,3) are useless button
		Locator AllStopButton = AllStop.locator("role=button");
		Thread.sleep(100000);

//		for (int i = 0; i < AllButtonCount; i++) {
//			System.out.println(RouteButtonLocator.nth(i).innerText());
//			WriteJson.updateJsonFile("Saved Stops", RouteButtonLocator.nth(i).innerText());
//		}

	}

	private void CustomizeSetting() throws Exception {
		// Appearance
		Locator appearance = page.locator("//html/body/div[3]/div[3]/div/ul/div[5]");
		updateSetting(appearance, "Settings.Appearance");

		// Power Saving Mode
		Locator powerSavingMode = page.locator("//html/body/div[3]/div[3]/div/ul/div[6]");
		updateSetting(powerSavingMode, "Settings.Power Saving Mode");

		// Platform Display Format
		Locator platformDisplayFormat = page.locator("//html/body/div[3]/div[3]/div/ul/div[7]");
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
		Locator etaFormat = page.locator("//html/body/div[3]/div[3]/div/ul/div[9]"); // Updated index if necessary
		updateSetting(etaFormat, "Settings.Eta Format");

		// Keyboard Layout
		Locator keyboardLayout = page.locator("//html/body/div[3]/div[3]/div/ul/div[10]"); // Updated index if necessary
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