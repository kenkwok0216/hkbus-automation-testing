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

import academy.teenfuture.crse.qa.hkbus.playwright.BaseTest;

//This test focuses on verifying navigation to the corresponding page and handling pop-ups.
//It does not cover the detailed functionality of the navigated pages or pop-ups.
//Moreover the update route database is also skipped due to the database is not handled by us
//
//Tests are organized into the following categories:
//1. Toggling settings on and off (e.g., enabling/disabling Auto Update Route Database).
//2. Handling pop-up dialogs.
//3. Navigating to different pages within the application.
//4. Navigating to external websites.
//5. Clicking Copy App URL button (system level pop of of copy app url will be skipped)
//
//Each test case is designed to isolate specific behaviors, ensuring better readability 
//and maintainability in the test suite.

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettingPageCheck extends BaseTest {

	@BeforeEach
	public void start() throws InterruptedException {
		// super.configure("Chrome").navigate("https://www.google.com");

		super.configure("Firefox").navigate("https://hkbus.app/en");
		// Locate button to Heart Page and click it
		Locator SettingPage = page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a");
		SettingPage.click();

	}

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
			// In this stage, skip the geolocation first since it seem to be rejecting
			// permission given in Playwright
			if (settingNames[i].equals("Geolocation")) {
				continue;
			}
			toggleSettingOnAndOff(locators[i], settingNames[i], testName);

		}
	}

	// This method is to test the button with pop up
	// i.e. install the app, Customize
	@Test
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

	@AfterEach
	public void endEach() {
		endEachTest();
	}

	@AfterAll
	public static void endAll() {
		endAllTest();
	}

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

	// Mockup for onAndOffStatus method
	private String onAndOffStatus(Locator settingLocator) {
		String statusText = settingLocator.locator("p").innerText().trim(); // Adjust the selector as needed
		return statusText.equalsIgnoreCase("On") ? "on" : "off";
	}

}
