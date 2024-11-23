package academy.teenfuture.crse.qa.hkbus.playwright.customizePopup;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import academy.teenfuture.crse.qa.hkbus.playwright.BaseTest;

//Route Filtering

public class EditRouteFilterTest extends BaseTest {

	@BeforeEach
	public void start() throws InterruptedException, IOException {
		super.configure("Chrome").navigate("https://hkbus.app/en");
		Thread.sleep(3000);

	};

	// @Disabled
	@Test
	public void editRouteFilter() throws InterruptedException {
		// nev to search page
		page.locator("//html/body/div[2]/div/div[1]/div[3]/a").click();
		Thread.sleep(2000);

		// home page
		page.locator("//html/body/div[2]/div/div[2]/div/ul/div[6]").click();
		Thread.sleep(2000);

		// click the tab
		page.locator("//html/body/div[3]/div[3]/div/ul/div[3]").click();
		Thread.sleep(2000);

		// //check the option is ONLY operating route
		// Locator initialValueLabel = page
		// .locator("(//span[contains(@class,'MuiSlider-root
		// MuiSlider-colorPrimary')])[2]");

		// initialValue = initialValueLabel.textContent();

		// close the popup
		page.press("body", "Escape");
		Thread.sleep(2000);

		// nav to search page
		page.locator("//*[@id=\"root\"]/div/div[3]/a[1]").click();
		Thread.sleep(2000);

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
