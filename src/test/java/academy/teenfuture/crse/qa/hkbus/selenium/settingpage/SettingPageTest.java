package academy.teenfuture.crse.qa.hkbus.selenium.settingpage;

import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import academy.teenfuture.crse.qa.hkbus.selenium.BaseTest;
import academy.teenfuture.crse.qa.hkbus.selenium.settingpage.strategy.CustomizeStrategy;
import academy.teenfuture.crse.qa.hkbus.selenium.settingpage.strategy.InstallAppStrategy;
import academy.teenfuture.crse.qa.hkbus.selenium.settingpage.strategy.PopUpHandler;
import academy.teenfuture.crse.qa.hkbus.selenium.settingpage.strategy.PopUpStrategy;
import academy.teenfuture.crse.qa.hkbus.selenium.settingpage.util.ReadJson;

/**
 * This class contains tests for the Setting Page of the HKBus application. It
 * includes tests for toggling settings, handling pop-ups, and navigating within
 * the application.
 * 
 * @author Ken Kwok
 * @see BaseTest
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettingPageTest extends BaseTest {

	@BeforeEach
	public void start() throws InterruptedException {
		// super.configure("Chrome").navigate("https://www.google.com");
		super.configureBrowser("Firefox").get("https://hkbus.app/en");

		Thread.sleep(3000);
		// Locate button to Heart Page and click it
		WebElement settingPageElement = driver.findElement(By.xpath("//*[@id='root']/div/div[1]/div[3]/a"));
		settingPageElement.click(); // Click the element
		Thread.sleep(3000);
	}

	/**
	 * Tests toggling settings on and off, excluding the Geolocation setting.
	 * 
	 * @throws IOException          if there is an issue reading from the JSON file
	 * @throws InterruptedException if the thread is interrupted while sleeping
	 */
	@Test
	@Disabled
	@Order(1)
	public void withOnandOffTest() throws IOException, InterruptedException {
		String testName = "on and off test";

		// Define an array of locators and their corresponding names
		By[] locators = new By[] { By.xpath("//*[@id='root']/div/div[2]/div/ul/div[4]"), // Geolocation
				By.xpath("//*[@id='root']/div/div[2]/div/ul/div[5]"), // Auto Update Route Database
				By.xpath("//*[@id='root']/div/div[2]/div/ul/div[10]") // Google Analytics
		};

		String[] settingNames = new String[] { "Geolocation", "Auto Update Route Database", "Google Analytics" };

		Thread.sleep(3000); // Consider using WebDriverWait instead for better practices

		// Iterate through each setting
		for (int i = 0; i < locators.length; i++) {
			// Skip the geolocation setting as per your original logic
			if (settingNames[i].equals("Geolocation")) {
				continue;
			}
			toggleSettingOnAndOff(locators[i], settingNames[i], testName);
		}
	}

	/**
	 * Tests the functionality of pop-up windows for specific settings.
	 * 
	 * @throws IOException          if there is an issue reading from the JSON file
	 * @throws InterruptedException if the thread is interrupted while sleeping
	 */
	@Test
	@Disabled
	@Order(2)
	public void popUpTest() throws IOException, InterruptedException {
		String testName = "pop up window test";

		// Define an array of locators and their corresponding names
		By[] locators = new By[] { By.xpath("//*[@id='root']/div/div[2]/div/ul/div[1]"), // Install App
				By.xpath("//*[@id='root']/div/div[2]/div/ul/div[6]") // Customize
		};

		String[] settingNames = new String[] { "Install the App", "Customize" };

		// Wait for the page to load (optional: you can use WebDriverWait here)
		Thread.sleep(3000);

		// Iterate through each setting
		for (int i = 0; i < locators.length; i++) {
			// Click on the locator
			driver.findElement(locators[i]).click();
			Thread.sleep(3000); // Optional: wait for the pop-up to appear

			// Declare a PopUpStrategy variable to hold the current strategy implementation
			PopUpStrategy strategy;
			// Determine the strategy based on the settingName
			if (settingNames[i].equals("Install the App")) {
				strategy = new InstallAppStrategy(); // Use InstallAppStrategy
			} else if (settingNames[i].equals("Customize")) {
				strategy = new CustomizeStrategy(); // Use CustomizeStrategy
			} else {
				// Log a message for any unknown setting names and skip to the next iteration
				System.out.println("Unknown setting name: " + settingNames[i]);
				continue; // Skip unknown settings
			}

			// Use the handler to execute the strategy
			PopUpHandler handler = new PopUpHandler(strategy);
			boolean result = handler.executeStrategy(driver); // Pass the WebDriver to execute the strategy

			if (result) {
				// Log success in Extent Report
				generateExtentTest(testName, result, settingNames[i] + " pop up is pass");
			} else {
				// Log failure in Extent Report
				generateExtentTest(testName, result, settingNames[i] + " pop up is failed");
			}
		}
	}

	/**
	 * Tests navigation within the application to various pages.
	 * 
	 * @throws IOException          if there is an issue reading from the JSON file
	 * @throws InterruptedException if the thread is interrupted while sleeping
	 */
	@Test
	@Disabled
	@Order(3)
	public void navigateWithInAppTest() throws IOException, InterruptedException {
		String testName = "Navigate with in the app Test";

		// Define an array of locators and their corresponding names
		By[] locators = new By[] { By.xpath("//*[@id='root']/div/div[2]/div/ul/div[7]"), // Data Export
				By.xpath("//*[@id='root']/div/div[2]/div/ul/div[8]"), // Data Import
				By.xpath("//*[@id='root']/div/div[2]/div/ul/a[8]"), // Privacy Policy
				By.xpath("//*[@id='root']/div/div[2]/div/ul/a[9]") // Terms and Conditions
		};

		// The button name and the corresponding page name suffix
		String[][] settingNames = new String[][] { { "Data Export", "export" }, { "Data Import", "import" },
				{ "Privacy Policy", "privacy" }, { "Terms and Conditions", "terms" } };

		// This is to store the result
		boolean error = false;

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		for (int i = 0; i < locators.length; i++) {
			// Click on the locator
			driver.findElement(locators[i]).click();

			// Wait for the page to load
			wait.until(ExpectedConditions.urlContains(settingNames[i][1])); // Wait until the URL contains the expected
																			// suffix
			Thread.sleep(3000);

			try {
				String currentUrl = driver.getCurrentUrl(); // Get the current URL after clicking

				if (currentUrl.endsWith(settingNames[i][1])) {
					// System.out.println(settingNames[i][0] + " is okay");
					driver.navigate().back(); // Go back
					wait.until(ExpectedConditions.urlContains("previous_suffix")); // Replace with actual suffix if
																					// needed
				} else {
					throw new Exception(settingNames[i][0] + " is navigating to wrong page");
				}

			} catch (Exception e) {
				super.generateExtentTest(testName, false, e.getMessage(), captureScreenshot());
				error = true;
			}
		}

		if (!error) { // If no error
			super.generateExtentTest(testName, true, "This test case passed for all navigation within the app");
		}
	}

	/**
	 * Tests navigation to other pages within the application.
	 * 
	 * @throws IOException if there is an issue reading from the JSON file
	 * @throws Exception   if any other exceptions occur during the test
	 */
	@Test
	@Disabled
	@Order(4)
	public void navigatePageTest() throws IOException, Exception {
		String testName = "Navigate to other page test";

		// Define an array of locators and their corresponding names
		By[] locators = new By[] { By.xpath("//*[@id='root']/div/div[2]/div/ul/a[1]"), // Smart Watch App
				// This is skipped due to the System pop up
				// By.xpath("//*[@id='root']/div/div[2]/div/ul/a[2]"), // Telegram Group
				By.xpath("//*[@id='root']/div/div[2]/div/ul/a[3]"), // Data Aggregation
				By.xpath("//*[@id='root']/div/div[2]/div/ul/a[5]"), // Source Code
				By.xpath("//*[@id='root']/div/div[2]/div/ul/a[6]"), // FAQ
				By.xpath("//*[@id='root']/div/div[2]/div/ul/a[7]") // Log Designer
		};

		String[] settingNames = new String[] { "Smart Watch App", // "Telegram Group",
				"Data Aggregation", "Source Code", "FAQ", "Log Designer" };

		// This is to store the result
		boolean error = false;

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		for (int i = 0; i < locators.length; i++) {
			// Click on the locator
			driver.findElement(locators[i]).click();

			String mainWindowHandle = driver.getWindowHandle();
			// System.out.println("mainWindowHandle : " + mainWindowHandle);
			Thread.sleep(3000);
			Set<String> windowHandles = driver.getWindowHandles();
			// Switch to the new pages
			for (String handle : windowHandles) {
				if (!handle.equals(mainWindowHandle)) {
					// The driver is handling the new pages
					driver.switchTo().window(handle);
					Thread.sleep(3000);
					break;
				}
			}

			// Wait for the new page to load completely
			try {
				wait.until(ExpectedConditions.urlContains(settingNames[i].toLowerCase()));
				// Get the current URL after navigation
				String currentUrl = driver.getCurrentUrl();

				// Read the expected URL from the JSON file
				String expectedUrl = ReadJson.readJsonFile(settingNames[i]);

				if (!expectedUrl.equals(currentUrl)) {
					throw new Exception(settingNames[i] + " navigated to the wrong page");
				}

			} catch (Exception e) {
				super.generateExtentTest(testName, false, e.getMessage(), captureScreenshot());
				error = true;

			} finally {
				driver.close();
				// the driver switch back
				driver.switchTo().window(mainWindowHandle);
				Thread.sleep(3000);
			}
		}

		if (!error) {
			super.generateExtentTest(testName, true, "This test case passed for all navigation to other pages");
		}
	}

	/**
	 * Cleans up after each test by quitting the WebDriver.
	 */
	@AfterEach
	public void end() {
		quitDriver();
	}

	/**
	 * Cleans up resources after all tests have been run.
	 */
	@AfterAll
	public static void endAll() {
		endAllTest();
	}

	/**
	 * Helper method to toggle a setting and verify its status.
	 * 
	 * @param settingLocator the locator for the setting element
	 * @param settingName    the name of the setting for logging
	 * @param testName       the name of the test case for logging
	 * @throws IOException if there is an issue reading from the JSON file
	 */
	// Helper method to toggle a setting and verify its status
	private void toggleSettingOnAndOff(By settingLocator, String settingName, String testName) throws IOException {
		// Create a scanner for user input
		Scanner scanner = new Scanner(System.in);

		// This loop tries to run twice to check both on to off and off to on
		for (int i = 0; i < 2; i++) {
			try {
				String currentStatus = onAndOffStatus(settingLocator);

				// Click to toggle the setting
				driver.findElement(settingLocator).click();

				// If toggling geolocation, wait for user confirmation
				if (settingName.equalsIgnoreCase("Geolocation")) {
					System.out.println("Press 'Enter' to continue after enabling geolocation...");
					scanner.nextLine(); // Wait for user input
				}

				Thread.sleep(3000); // Consider using WebDriverWait instead for better practices

				// Verify the new status
				String newStatus = onAndOffStatus(settingLocator);
				if (currentStatus.equals("on") && !newStatus.equals("off")) {
					throw new Exception("The status of " + settingName + " failed to change to off.");
				} else if (currentStatus.equals("off") && !newStatus.equals("on")) {
					throw new Exception("The status of " + settingName + " failed to change to on.");
				}

			} catch (Exception e) {
				generateExtentTest(testName, false, e.getMessage(), super.captureScreenshot());
			}
		}
	}

	/**
	 * Retrieves the current on/off status of a setting.
	 * 
	 * @param settingLocator the locator for the setting element
	 * @return the current status as "on" or "off"
	 */
	private String onAndOffStatus(By settingLocator) {
		// Find the status element (adjust the selector as needed)
		WebElement statusElement = driver.findElement(settingLocator).findElement(By.tagName("p"));
		String statusText = statusElement.getText().trim();
		return statusText.equalsIgnoreCase("On") ? "on" : "off";
	}

}
