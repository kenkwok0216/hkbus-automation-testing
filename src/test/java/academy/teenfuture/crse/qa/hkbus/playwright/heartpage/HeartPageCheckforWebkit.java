package academy.teenfuture.crse.qa.hkbus.playwright.heartpage;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

/**
 * The {@code HearPageCheckforWebkit} class extends the {@link HeartPageCheck}
 * class to provide test functionality specifically for the WebKit browser.
 * 
 * <p>
 * This class overrides the {@code start()} method to configure the Playwright
 * test environment to use the WebKit browser instead of the default
 * configuration. All test methods from the {@code HeartPageCheck} class are
 * inherited automatically.
 * </p>
 * 
 * <p>
 * The sequence of operations in the {@code start()} method includes:
 * <ul>
 * <li>Configuring Playwright to use the WebKit browser.</li>
 * <li>Navigating to the HKBus application homepage.</li>
 * <li>Clicking on the button to access the Heart Page.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Note: Thread sleep statements are included for synchronization purposes and
 * may need to be adjusted based on the application's responsiveness.
 * </p>
 * 
 * @author Ken Kwok
 * @see HeartPageCheck
 */

public class HeartPageCheckforWebkit extends HeartPageCheck {

	/**
	 * Sets up the test environment before each test. This method is overridden to
	 * configure Playwright to use the WebKit browser and navigate to the Heart Page
	 * of the HKBus application.
	 *
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
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