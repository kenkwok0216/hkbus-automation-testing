package academy.teenfuture.crse.qa.hkbus.playwright;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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
	public void fontSizeChengeTest() throws InterruptedException {

		// Start getting initial Value
		String initialValue;

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
			}

			newpage.close();
			newpage = null;
		}
	}

	@Test
	// @Disabled
	public void fontSizeChengeTest_notused() throws InterruptedException {
		Playwright playwright = Playwright.create();
		// Part 1 check the initial value of the font slider
		int count = 0;
		for (int i = 9; i < 10; i++) {

			System.out.println("Iteration: " + (i + 1));

			BrowserType browserType = playwright.chromium();
			Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
			BrowserContext browserContext = browser.newContext();
			Page page = browserContext.newPage();

			// nevigate to web home page of hkbus
			page.navigate("https://hkbus.app/zh/");
			// Thread.sleep(5000);

			// nevigate to setting page
			page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
			// Thread.sleep(2000);

			// nevigate to perosnal setting
			page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
			// Thread.sleep(2000);

			// Locate the class element of the slider and check whether it is 14
			Locator valueLabel = page.locator("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]");

			// read the value and print it out
			String value = valueLabel.textContent();
			System.out.println("font size = " + value);

			if ("14".equals(value)) {
				count++;
			}
			System.out.println("Total number of font size = 14: " + count);

			browser.close();
		}

		// part 2.1 check default font size =14
		BrowserType browserType = playwright.chromium();
		Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
		BrowserContext browserContext = browser.newContext();
		Page page = browserContext.newPage();

		// nevigate to web home page of hkbus
		page.navigate("https://hkbus.app/zh/");
		// Thread.sleep(5000);

		// nevigate to setting page
		page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
		// Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		// Thread.sleep(2000);

		// get font size(only apply in setting page)
		Locator font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");

		String fontSizeValue = font.evaluate("el => getComputedStyle(el).fontSize").toString();

		System.out.println(fontSizeValue);

		// part 2.2 leave font page
		page.waitForTimeout(2000);
		page.locator("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]").click();
		// Thread.sleep(2000);

		/*
		 * 
		 * //display each page //click and display home page page.waitForTimeout(2000);
		 * page.
		 * locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[1]"
		 * ).click(); //Thread.sleep(2000);
		 * 
		 * //click and display Stops page page.waitForTimeout(2000); page.
		 * locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[2]"
		 * ).click(); //Thread.sleep(2000);
		 * 
		 * //click and display Search page page.waitForTimeout(2000); page.
		 * locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]"
		 * ).click(); //Thread.sleep(2000);
		 * 
		 * //click and display P2P page page.waitForTimeout(2000); page.
		 * locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[4]"
		 * ).click(); //Thread.sleep(2000);
		 * 
		 * //click and display Notice page page.waitForTimeout(2000); page.
		 * locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[5]"
		 * ).click(); //Thread.sleep(2000);
		 * 
		 * //click and display Heart page page.waitForTimeout(2000); page.
		 * locator("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[6]"
		 * ).click(); //Thread.sleep(2000);
		 * 
		 * //finish display each page
		 * 
		 */

		// Part 3 change font size to 10(smallest size)
		// nevigate to setting page
		page.locator("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]").click();
		// Thread.sleep(2000);

		// nevigate to perosnal setting
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		// Thread.sleep(2000);

		// Locate the class element of the slider and check whether it is 14
		Locator valueLabel2 = page.locator("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]");

		// nevigate to slider value = 18
		valueLabel2.click();
		Thread.sleep(5000);
		// nevigate to value =10
		valueLabel2.press("ArrowLeft");
		Thread.sleep(1000);
		// get font size(only apply in setting page)
		font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");

		fontSizeValue = font.evaluate("el => getComputedStyle(el).fontSize").toString();

		System.out.println(fontSizeValue);

		valueLabel2.press("ArrowLeft");
		font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");

		fontSizeValue = font.evaluate("el => getComputedStyle(el).fontSize").toString();

		System.out.println(fontSizeValue);
		Thread.sleep(1000);
		valueLabel2.press("ArrowLeft");
		Thread.sleep(1000);
		valueLabel2.press("ArrowLeft");
		Thread.sleep(1000);
		// valueLabel2.press("ArrowLeft");
		// Thread.sleep(5000);

		// get font size(only apply in setting page)
		font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");
		fontSizeValue = font.evaluate("el => getComputedStyle(el).fontSize").toString();
		System.out.println(fontSizeValue);

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
		// nevigate to value =10
		valueLabel3.press("ArrowRight");
		// Thread.sleep(5000);
		valueLabel3.press("ArrowRight");
		// Thread.sleep(5000);
		valueLabel3.press("ArrowRight");
		// Thread.sleep(5000);
		valueLabel3.press("ArrowRight");
		// valueLabel2.press("ArrowLeft");
		// Thread.sleep(5000);
		// get font size(only apply in setting page)
		font = page.locator("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]");
		fontSizeValue = font.evaluate("el => getComputedStyle(el).fontSize").toString();
		System.out.println(fontSizeValue);

		// Close the browser
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
