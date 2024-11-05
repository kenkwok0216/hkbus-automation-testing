package academy.teenfuture.crse.qa.hkbus.playwright;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

public class SettingPageCheck extends BaseTest {

	@BeforeEach
	public void start() throws InterruptedException {
		// super.configure("Chrome").navigate("https://www.google.com");
		super.configure("firefox").navigate("https://hkbus.app/en");
		// Locate button to Heart Page and click it
		Locator SettingPage = page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a");
		SettingPage.click();
		Thread.sleep(3000);
	}

	@Test
	public void FirstTest() {

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
