package academy.teenfuture.crse.qa.hkbus.playwright;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Template test class
 */
public class TestFirst extends BaseTest {

	@BeforeEach
	public void start() {
		// super.configure("Chrome").navigate("https://www.google.com");
		super.configure("firefox").navigate("https://hkbus.app/en/");

	}

	// No need to record video
	@Test
	@Disabled
	public void fontSizeChengeTest() throws InterruptedException, IOException {

		// Start getting initial Value
		String initialValue;
		Boolean error = false;

		// nevigate to setting page
		page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
		Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		Thread.sleep(2000);

		// Locate the class element of the slider and check whether it is 14
		Locator initialValueLabel = page
				.locator("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]");

		// read the value and print it out
		initialValue = initialValueLabel.textContent();

		// intial value is got

		// Start iteration to check whether each time is the same
		for (int i = 0; i < 10; i++) {

			System.out.println("Iteration: " + (i + 1));

			Page newpage = browserContext.newPage();

			newpage.navigate("https://hkbus.app/en/");

			Thread.sleep(5000);

			// nevigate to setting page
			newpage.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
			Thread.sleep(2000);

			// nevigate to perosnal setting
			newpage.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
			Thread.sleep(2000);

			// Locate the class element of the slider and check whether it is 14
			Locator valueLabel = newpage
					.locator("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]");

			// read the value and print it out
			String value = valueLabel.textContent();

			if (value.equals(initialValue)) {

				System.out.println("Value is maintained: " + value);

			} else {

				System.out.println("Value has changed: " + value);
				error = true;
				super.generateExtentTest("Initial Value unchange", false, "The intial value changed in iteration: " + i,
						page.screenshot());
				break;
			}

			newpage.close();
			newpage = null;
		}

		if (!error) {
			super.generateExtentTest("Initial Value unchange", true, "This test pass");
		}

	}

	@Test
	// @Disabled
	public void fontSizeChengeTest_notused() throws InterruptedException, IOException {
		// Part 1 change font size to 10(smallest size)
		// nevigate to setting page
		page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
		// Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		// Thread.sleep(2000);

		// Locate the class element of the slider and check whether it is 14
		Locator valueLabel = page.locator("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]");

		// nevigate to slider value = 18
		valueLabel.click();
		Thread.sleep(5000);
		// nevigate to value =10
		valueLabel.press("ArrowLeft");
		Thread.sleep(1000);
		// get font size(only apply in setting page)
		// font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");

		// fontSizeValue = font.evaluate("el =>
		// getComputedStyle(el).fontSize").toString();

		// System.out.println(fontSizeValue);

		valueLabel.press("ArrowLeft");
		// font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");

		// fontSizeValue = font.evaluate("el =>
		// getComputedStyle(el).fontSize").toString();

		// System.out.println(fontSizeValue);
		Thread.sleep(1000);
		valueLabel.press("ArrowLeft");
		Thread.sleep(1000);
		valueLabel.press("ArrowLeft");
		Thread.sleep(1000);
		// valueLabel2.press("ArrowLeft");
		// Thread.sleep(5000);

		// get font size(only apply in setting page)
		Locator font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");
		String fontSizeValue = font.evaluate("el => getComputedStyle(el).fontSize").toString();
		System.out.println(fontSizeValue);

		// creat report for font size 10
		if (fontSizeValue.equals("10")) {
			super.generateExtentTest("fontSizeTest 10", true, "This fontSizeTest pass");
		} else {
			super.generateExtentTest("fontSizeTest 10", false, "The font is not 10", page.screenshot());
		}

		// Part 4 change font size to 26(biggest size)
		// part 2.1 leave font page
		page.waitForTimeout(2000);
		page.locator("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]").click();
		// Thread.sleep(2000);
		// nevigate to setting page
		page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
		// Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		// Thread.sleep(2000);

		// Locate the class element of the slider and check whether it is 14
		Locator valueLabel3 = page.locator("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]");

		// nevigate to slider value = 18
		valueLabel3.click();
		// Thread.sleep(5000);
		// nevigate to value =26
		valueLabel3.press("ArrowRight");
		Thread.sleep(1000);
		valueLabel3.press("ArrowRight");
		Thread.sleep(1000);
		valueLabel3.press("ArrowRight");
		Thread.sleep(1000);
		valueLabel3.press("ArrowRight");
		// valueLabel2.press("ArrowLeft");
		Thread.sleep(1000);
		// get font size(only apply in setting page)
		font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");
		fontSizeValue = font.evaluate("el => getComputedStyle(el).fontSize").toString();
		System.out.println(fontSizeValue);

		Thread.sleep(1000);

		// Close the browser
		// browser.close();
		// creat report for font size 26
		if (fontSizeValue.equals("26")) {
			super.generateExtentTest("fontSizeTest 26", true, "This fontSizeTest pass");
		} else {
			super.generateExtentTest("fontSizeTest 26", false, "The font is not 26", page.screenshot());
		}

	}

	@Test
	@Disabled
	public void keyboardLayoutCheck() throws InterruptedException, IOException {
		// part 1 check if order=123456789c0b
		// Playwright playwright = Playwright.create();
		// BrowserType browserType = playwright.chromium();
		// Browser browser = browserType.launch(new
		// BrowserType.LaunchOptions().setHeadless(false));
		// BrowserContext browserContext = browser.newContext();
		// Page page = browserContext.newPage();

		// nevigate to web home page of hkbus
		// page.navigate("https://hkbus.app/zh/");
		// Thread.sleep(5000);

		Boolean error = false;

		// nevigate to setting page
		page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
		// Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		// Thread.sleep(5000);

		// find the order = 123456789c0b
		Locator settingKeyboardLayoutButton = page
				.locator("//li[contains(@class,'MuiListItem-root MuiListItem-gutters')]/following-sibling::div[1]");
		String layoutOrder = settingKeyboardLayoutButton.locator("p").innerText().trim();
		System.out.println("value : " + layoutOrder);

		// check buttonboard
		// leave setting page
		page.locator("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]").click();
		Thread.sleep(2000);

		// click and display Search page
		page.waitForTimeout(2000);
		page.locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]").click();
		Thread.sleep(2000);

		// check if buttonboard display according to the order
		Locator buttonboard = page.locator("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]");
		Locator NumberButton = buttonboard.locator("button");
		System.out.println(NumberButton.nth(0).innerText().trim());

		for (int i = 0; i < 9; i++) {
			Boolean numberMatch = NumberButton.nth(i).innerText().trim()
					.equals(Character.toString(layoutOrder.charAt(i)));
			System.out.println(numberMatch);
			if (numberMatch) {
				System.out.println("NumberMatch");
			} else {
				super.generateExtentTest("Layout Order", false, "The layout order is wrong", page.screenshot());
				error = true;
				break;
			}
		}

		// part 2.1 change the order
		// nevigate to setting page
		page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a").click();
		// Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		// Thread.sleep(5000);

		// click and change the layout order
		settingKeyboardLayoutButton.click();
		// Thread.sleep(5000);

		// check if value = 789456123c0b
		String layoutOrder2 = settingKeyboardLayoutButton.locator("p").innerText().trim();
		System.out.println("value : " + layoutOrder2);
		// Thread.sleep(5000);

		// part 2.2 change the order
		// check buttonboard
		// leave setting page
		page.locator("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]").click();
		Thread.sleep(2000);

		// click and display Search page
		page.waitForTimeout(2000);
		page.locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]").click();
		Thread.sleep(2000);

		// check if buttonboard display according to the order
		Locator buttonboard2 = page.locator("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]");
		Locator NumberButton2 = buttonboard2.locator("button");
		System.out.println(NumberButton2.nth(0).innerText().trim());

		for (int i = 0; i < 9; i++) {
			Boolean numberMatch = NumberButton2.nth(i).innerText().trim()
					.equals(Character.toString(layoutOrder2.charAt(i)));
			System.out.println(numberMatch);
			if (numberMatch) {
				System.out.println("NumberMatch");
			} else {
				super.generateExtentTest("Layout Order", false, "The layout order is wrong", page.screenshot());
				error = true;
				break;
			}
		}

		if (!error) {
			super.generateExtentTest("Layout Order", true, "This test case pass");
		}

		// browser.close();
	}

	@Test
	@Disabled
	public void historyOnOff() throws InterruptedException, IOException {
		// part 1 check if history is On
		// Playwright playwright = Playwright.create();
		// BrowserType browserType = playwright.chromium();
		// Browser browser = browserType.launch(new
		// BrowserType.LaunchOptions().setHeadless(false));
		// BrowserContext browserContext = browser.newContext();
		// Page page = browserContext.newPage();
		//
		// // nevigate to web home page of hkbus
		// page.navigate("https://hkbus.app/en/");
		Thread.sleep(2000);

		// Check if the history button is On
		// nevigate to setting page
		page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
		// Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		// Thread.sleep(5000);

		// Scroll down the page
		page.mouse().wheel(0, 300);
		Locator history = page.locator("//html/body/div[3]/div[3]/div/ul/div[11]");
		history.scrollIntoViewIfNeeded();
		Thread.sleep(5000);

		// chech if the value is On
		Locator historyOn = history.locator("p");
		System.out.println("Search historty value: " + historyOn.nth(0).innerText().trim());
		// Thread.sleep(5000);

		// leave setting page
		page.locator("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]").click();
		// Thread.sleep(5000);

		// click and display Search page
		page.locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]").click();
		Thread.sleep(2000);

		// // check whether recent button exist
		// Locator recent =
		// page.locator("//*[@id=\"root\"]/div/div[2]/div[1]/div[1]/div/div/div/button[1]");
		// if (recent.count() > 0) {
		// System.out.println("recent button exist"); // The button exists
		// } else {
		// System.out.println("recent button not exist"); // The button does not exist
		// }

		// Part 2.1 check if recent button exist
		Locator header = page.locator("//*[@id=\"root\"]/div/div[2]/div[1]/div[1]/div/div/div");
		Locator button = header.locator("button");
		// System.out.println(button.nth(0).innerText().trim());
		// Get the count of buttons
		int buttonCount = button.count();
		boolean recentExists = false; // flag to check if "recent" exist

		// Print all button texts in the header
		for (int i = 0; i < buttonCount; i++) {
			String buttonText = button.nth(i).innerText().trim(); // Get the text of each button
			System.out.println("Button " + (i + 1) + ": " + buttonText); // Print the button text

			// check if the button text matches "Recent"
			if (buttonText.equalsIgnoreCase("Recent")) {
				recentExists = true; // set flag to true if found
				break;
			}
		}

		// print the result base on the flag
		if (recentExists) {
			System.out.println("Recent exists");
			super.generateExtentTest("Recent button enable", true, "This test case pass");
		} else {
			System.out.println("Recent not exists");
			super.generateExtentTest("Recent button enable", false, "Recent should be exists in this case",
					page.screenshot());
		}

		// finish part 1
		// nevigate to setting page
		page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
		// Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		// Thread.sleep(5000);

		// Scroll down the page
		page.mouse().wheel(0, 300);
		// Locator history =page.locator("//html/body/div[3]/div[3]/div/ul/div[11]");
		history.scrollIntoViewIfNeeded();

		// Part 2
		// click and Off the history
		Locator history2 = page.locator("//html/body/div[3]/div[3]/div/ul/div[11]");
		// Thread.sleep(5000);
		history2.scrollIntoViewIfNeeded();
		// Thread.sleep(5000);
		history2.click();
		// Thread.sleep(5000);

		// chech if the value is Off
		Locator historyOff = history2.locator("p");
		System.out.println("Search historty value: " + historyOff.nth(0).innerText().trim());
		// Thread.sleep(5000)

		// Part 2.1 check if recent button exist
		page.locator("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]").click();
		// Thread.sleep(5000);

		// click and display Search page
		page.locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]").click();
		// Thread.sleep(5000);

		Locator header2 = page.locator("//*[@id=\"root\"]/div/div[2]/div[1]/div[1]/div/div/div");
		Locator button2 = header2.locator("button");
		// System.out.println(button.nth(0).innerText().trim());
		// Get the count of buttons
		int buttonCount2 = button2.count();
		boolean recentExists2 = false; // flag to check if "recent" exist

		// Print all button texts in the header
		for (int i = 0; i < buttonCount2; i++) {
			String buttonText2 = button2.nth(i).innerText().trim(); // Get the text of each button
			System.out.println("Button " + (i + 1) + ": " + buttonText2); // Print the button text

			// check if the button text matches "Recent"
			if (buttonText2.equalsIgnoreCase("Recent")) {
				recentExists2 = true; // set flag to true if found
				break;
			}
		}

		// print the result base on the flag
		if (recentExists2) {
			System.out.println("Recent exists");
			super.generateExtentTest("Recent button disable", false, "Recent should not exits is this case",
					page.screenshot());
		} else {
			System.out.println("Recent not exists");
			super.generateExtentTest("Recent button disable", true, "This test case passed");
		}

		// browser.close();
	}

	@AfterEach
	public void endEach() {
		endEachTest();
	}

	@AfterAll
	public static void endAll() {
		endAllTest();
	}
}
