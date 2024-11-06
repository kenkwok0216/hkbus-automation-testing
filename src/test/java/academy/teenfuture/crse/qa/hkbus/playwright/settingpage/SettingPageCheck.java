package academy.teenfuture.crse.qa.hkbus.playwright.settingpage;

import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
//5. Clicking Copy App URL button
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
	// i.e. install the app, Customize,
	@Test
	@Order(1)
	public void popUpTest() throws IOException, InterruptedException {

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
					throw new Exception("The status failed to change to off.");
				} else if (currentStatus.equals("off") && !newStatus.equals("on")) {
					throw new Exception("The status failed to change to on.");
				}

			} catch (Exception e) {
				// Generate test report for the failure with specific setting name in the
				// message
				generateExtentTest(testName, false, e.getMessage(), settingLocator.screenshot());
			}
		}
	}

	// Mockup for onAndOffStatus method (implement this according to your logic)
	private String onAndOffStatus(Locator settingLocator) {
		String statusText = settingLocator.locator("p").innerText().trim(); // Adjust the selector as needed
		return statusText.equalsIgnoreCase("On") ? "on" : "off";
	}
}
