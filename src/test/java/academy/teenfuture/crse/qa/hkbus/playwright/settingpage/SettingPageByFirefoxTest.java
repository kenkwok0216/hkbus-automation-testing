package academy.teenfuture.crse.qa.hkbus.playwright.settingpage;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

public class SettingPageByFirefoxTest extends SettingPageTest {

	/**
	 * Sets up the environment before each test. Navigates to the settings page of
	 * the HKBus application.
	 *
	 * @throws InterruptedException if the thread is interrupted while sleeping.
	 */
	@Override
	@BeforeEach
	public void start() throws InterruptedException {
		// super.configure("Chrome").navigate("https://www.google.com");

		super.configure("Firefox").navigate("https://hkbus.app/en");
		Thread.sleep(3000);
		// Locate button to Heart Page and click it
		Locator SettingPage = page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a");
		SettingPage.click();
		Thread.sleep(3000);

	}
}