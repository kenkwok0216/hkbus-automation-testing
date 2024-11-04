package academy.teenfuture.crse.qa.hkbus.playwright;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

public class HearPageCheckforWebkit extends HeartPageCheck {

	@BeforeEach
	@Override
	public void start() throws InterruptedException {
		// Configure to use Webkit
		super.configure("Webkit", true).navigate("https://hkbus.app/en");
		// Locate button to Heart Page and click it
		Locator HeartPage = page.locator("//*[@id=\"root\"]/div/div[3]/a[6]");
		HeartPage.click();
		Thread.sleep(1000);
	}

	// All test methods from HeartPageCheck will automatically be included
}