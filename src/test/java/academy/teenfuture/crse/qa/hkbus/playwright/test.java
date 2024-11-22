package academy.teenfuture.crse.qa.hkbus.playwright;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

/**
 * Template test class
 */
public class test extends BaseTest {

	@BeforeEach
	public void start() {
		// super.configure("Chrome").navigate("https://www.google.com");
		super.configure("firefox").navigate("https://hkbus.app/en/settings");
	}

	@Test
	public void simpleDummyTest1() throws InterruptedException {
		page.locator("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]").click();
		Locator Search = page.locator("//html/body/div[3]/div[3]/div/ul/div[11]");
		Locator SearchOn = Search.locator("p");
		System.out.println(SearchOn.nth(0).innerText().trim());

		// page.press("body", "ESC");
		page.locator("//*[@id=\"root\"]/div/div[3]/a[3]").click();
		Thread.sleep(1000);

		Locator test = page.locator("//*[@id=\"root\"]/div/div[2]/div[1]/div[1]/div/div").locator("button");

		System.out.println(test.count());

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
