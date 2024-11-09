package academy.teenfuture.crse.qa.hkbus.playwright.settingpage;

import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import academy.teenfuture.crse.qa.hkbus.playwright.BaseTest;
import academy.teenfuture.crse.qa.hkbus.playwright.settingpage.util.ReadJson;

//This test focuses on verifying navigation to the corresponding page and handling pop-ups.
//It does not cover the detailed functionality of the navigated pages or pop-ups.
//Moreover the update route database is also skipped due to the database is not handled by us
//
//Tests are organized into the following categories:
//1. Toggling settings on and off (e.g., enabling/disabling Auto Update Route Database).
//2. Handling pop-up dialogs.
//3. Navigating to different pages within the application.
//4. Navigating to external websites.
//
//Each test case is designed to isolate specific behaviors, ensuring better readability 
//and maintainability in the test suite.

/**
 * Test class for verifying the functionality of the Settings page in the HKBus
 * application.
 *
 * This class includes tests for: 1. Toggling various settings on and off (e.g.,
 * Geolocation, Auto Update Route Database). 2. Handling pop-up dialogs for app
 * installation and customization. 3. Navigating to different pages within the
 * application. 4. Navigating to external websites.
 *
 * Each test is designed to isolate specific behaviors, ensuring better
 * readability and maintainability.
 * 
 * @author Ken Kwok
 * @see BaseTest
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettingPageCheck extends BaseTest {

	/**
	 * Sets up the environment before each test. Navigates to the settings page of
	 * the HKBus application.
	 *
	 * @throws InterruptedException if the thread is interrupted while sleeping.
	 */
	@BeforeEach
	public void start() throws InterruptedException {
		// super.configure("Chrome").navigate("https://www.google.com");

		super.configure("Firefox").navigate("https://hkbus.app/en");
		// Locate button to Heart Page and click it
		Locator SettingPage = page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a");
		SettingPage.click();
		Thread.sleep(100000);

	}

	@Test
	public void test() {
		
	}
	
	
	/**
	 * Tests the toggling of settings on and off. Currently disabled.
	 *
	 * @throws IOException          if there is an issue with input/output
	 *                              operations.
	 * @throws InterruptedException if the thread is interrupted while sleeping.
	 */
	// This method is to test the button with on and off in this page
	// i.e. Geolocation, Auto Update Route Database and Google Analytics
	@Test
	@Disabled
	@Order(1)
	public void withOnandOffTest() throws IOException, InterruptedException {
		String testName = "on and off test";

		// Define an array of locators and their corresponding names
		Locator[] locators = new Locator[] { page.locator("//*[@id='root']/div/div[2]/div/ul/div[4]"), // Geolocation
				page.locator("//*[@id='root']/div/div[2]/div/ul/div[5]"), // Auto Update Route Database
				page.locator("//*[@id='root']/div/div[2]/div/ul/div[10]") // Google Analytics
		};

		String[] settingNames = new String[] { "Geolocation", "Auto Update Route Database", "Google Analytics" };

		Thread.sleep(3000);

		// Iterate through each setting
		for (int i = 0; i < locators.length; i++) {
			// In this stage, skip the geolocation 
			// since even if the geolocation is given
			// It is not able to enable the geolocations
			// BUT it works manually using incognito mode
			if (settingNames[i].equals("Geolocation")) {
				continue;
			}
			toggleSettingOnAndOff(locators[i], settingNames[i], testName);

		}
	}

	/**
	 * Tests the behavior of pop-up dialogs related to installing the app and
	 * customizing settings. Currently disabled.
	 *
	 * @throws IOException          if there is an issue with input/output
	 *                              operations.
	 * @throws InterruptedException if the thread is interrupted while sleeping.
	 */
	// This method is to test the button with pop up
	// i.e. install the app, Customize
	@Test
	@Disabled
	@Order(2)
	public void popUpTest() throws IOException, InterruptedException {
		String testName = "pop up window test";

		// Define an array of locators and their corresponding names
		Locator[] locators = new Locator[] { page.locator("//*[@id='root']/div/div[2]/div/ul/div[1]"), // install app
				page.locator("//*[@id='root']/div/div[2]/div/ul/div[6]") // Customize
		};

		String[] settingNames = new String[] { "Install the App", "Customize" };

		Thread.sleep(3000);

		// Iterate through each setting
		for (int i = 0; i < locators.length; i++) {
			// Declare a PopUpStrategy variable to hold the current strategy implementation
			PopUpStrategy strategy;
			// Determine the strategy based on the settingName
			if (settingNames[i].equals("Install the App")) {
				// Use InstallAppStrategy if the setting name matches "Install the App"
				strategy = new InstallAppStrategy();
			} else if (settingNames[i].equals("Customize")) {
				// Use CustomizeStrategy if the setting name matches "Customize"
				strategy = new CustomizeStrategy();
			} else {
				// Log a message for any unknown setting names and skip to the next iteration
				System.out.println("Unknown setting name: " + settingNames[i]);
				continue; // Skip unknown settings
			}

			// Use the handler to execute the strategy
			PopUpHandler handler = new PopUpHandler(strategy);
			boolean result = handler.executeStrategy(page); // Execute the strategy for the current setting

			if (result) {
				// Log success in Extent Report
				super.generateExtentTest(testName, result, settingNames[i] + " pop up is pass");
			} else {
				// Log failure in Extent Report
				super.generateExtentTest(testName, result, settingNames[i] + " pop up is failed");
			}
		}

	}

	/**
	 * Tests navigation within the app to various internal pages. Currently
	 * disabled.
	 *
	 * @throws IOException if there is an issue with input/output operations.
	 */
	// This method is to test the button that will navigate to the pages within the
	// app, difference from popUpTest, i try use other method to handle it
	// i.e. data export, data import, Privacy Policy and Terms and Conditions
	@Test
	@Disabled
	@Order(3)
	public void NavigateWithInAppTest() throws IOException {
		String testName = "Navigate with in the app Test";

		// Define an array of locators and their corresponding names
		Locator[] locators = new Locator[] { page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/li"), // Data export
				page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[8]"), // Data import
				page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/a[8]"), // Privacy Policy
				page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/a[9]") // Terms and Conditions

		};

		// The button name and the corresponding page name suffix
		String[][] SettingName = new String[][] { { "Data Export", "export" }, { "Data Import", "import" },
				{ "Privacy Policy", "privacy" }, { "terms and conditions", "terms" } };

		// This is to store the result
		boolean error = false;

		for (int i = 0; i < locators.length; i++) {

			locators[i].click();

			// waiting to allow the page to load
			page.waitForLoadState();

			try {

				String currentUrl = page.url(); // Get the current URL after clicking

				if (currentUrl.endsWith(SettingName[i][1])) {

					// System.out.println(SettingName[i][0] + " is okay");
					page.goBack();

				} else {

					throw new Exception(SettingName[i][0] + " is navigating to wrong page");

				}

			} catch (Exception e) {

				super.generateExtentTest(testName, false, e.getMessage(), page.screenshot());
				error = true;

			}

		}

		if (!error) { // if no error

			super.generateExtentTest(testName, true, "This test case is pass for all navigation with in app");

		}

	}

	/**
	 * Tests navigation to external pages from the app. Currently disabled.
	 *
	 * @throws IOException if there is an issue with input/output operations.
	 * @throws Exception   if any other error occurs during the test.
	 */
	// This method is to test button that will navigate to external page
	// i.e. Smart Watch App, Telegram Group, Data Aggregation, Donate, Source code,
	// FAQ, Log Designer (Donate is skipped since it will changed the donate
	// company)
	// For this test, we will use json file way to do it
	// Base on the result, it may return error sometimes, even no error occur
	@Test
	@Disabled
	@Order(4)
	public void NavigatePageTest() throws IOException, Exception {
		String testName = "Navigate to other page test";

		// Define an array of locators and their corresponding names
		Locator[] locators = new Locator[] { page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/a[1]"), // Smart Watch
																										// App
				page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/a[2]"), // Telegram Group
				page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/a[3]"), // Data Aggregation
				page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/a[5]"), // Source code
				page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/a[6]"), // FAQ
				page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/a[7]") // Log Designer

		};

		String[] settingNames = new String[] { "Smart Watch App", "Telegram Group", "Data Aggregation", "Source Code",
				"FAQ", "Log Designer" };

		// This is to store the result
		boolean error = false;

		for (int i = 0; i < locators.length; i++) {

			locators[i].click();

			Page newPage = page.context().waitForPage(() -> {
				// no need to do anything in the listener
			});

			try {

				// Wait for the new page to load completely
				newPage.waitForLoadState();

				Thread.sleep(5000);

				// Get the current URL after navigation
				String currentUrl = newPage.url();

				// Read the expected URL from the JSON file
				String expectedUrl = ReadJson.readJsonFile(settingNames[i]);

				System.out.println(currentUrl);

				if (!expectedUrl.equals(currentUrl)) {
					throw new Exception(settingNames[i] + " navigate to wrong page");
				}

				Thread.sleep(3000);

			} catch (Exception e) {

				super.generateExtentTest(testName, false, e.getMessage(), newPage.screenshot());
				error = true;

			} finally {

				newPage.close();
				Thread.sleep(3000);

			}

		}

		if (!error) {

			super.generateExtentTest(testName, true, "This test case is pass for all navigation to other apge");

		}

	}

	/**
	 * Cleans up after each test.
	 */
	@AfterEach
	public void endEach() {
		endEachTest();
	}

	/**
	 * Cleans up resources after all tests have run.
	 */
	@AfterAll
	public static void endAll() {
		endAllTest();
	}

	/**
	 * Toggles a given setting on and off and verifies its status.
	 *
	 * @param settingLocator the Locator for the setting to toggle.
	 * @param settingName    the name of the setting being toggled.
	 * @param testName       the name of the test case.
	 * @throws IOException if there is an issue with input/output operations.
	 */
	// Helper method to toggle a setting and verify its status
	private void toggleSettingOnAndOff(Locator settingLocator, String settingName, String testName) throws IOException {
		// Create a scanner for user input
		Scanner scanner = new Scanner(System.in);
		// This loop try to run for twice to check both on to off and off to on
		for (int i = 0; i < 2; i++) {
			try {
				String currentStatus = onAndOffStatus(settingLocator);

				// Click to toggle the setting
				settingLocator.click();

				// If toggling geolocation, wait for user confirmation
				if (settingName.equalsIgnoreCase("geolocation")) {
					System.out.println("Press 'Enter' to continue after enabling geolocation...");
					scanner.nextLine(); // Wait for user input
				}

				Thread.sleep(10000);

				// Verify the new status
				String newStatus = onAndOffStatus(settingLocator);
				if (currentStatus.equals("on") && !newStatus.equals("off")) {
					throw new Exception("The status of " + settingName + " failed to change to off.");
				} else if (currentStatus.equals("off") && !newStatus.equals("on")) {
					throw new Exception("The status of " + settingName + " failed to change to on.");
				}

			} catch (Exception e) {
				// Generate test report for the failure with specific setting name in the
				// message
				generateExtentTest(testName, false, e.getMessage(), settingLocator.screenshot());
			}
		}
	}

	/**
	 * Retrieves the current status of the setting (on or off).
	 *
	 * @param settingLocator the Locator for the setting.
	 * @return the current status as a string ("on" or "off").
	 */
	// Mockup for onAndOffStatus method
	private String onAndOffStatus(Locator settingLocator) {
		String statusText = settingLocator.locator("p").innerText().trim(); // Adjust the selector as needed
		return statusText.equalsIgnoreCase("On") ? "on" : "off";
	}

}
