package academy.teenfuture.crse.qa.hkbus.selenium.heartpage;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * This class extends the HeartPageCheck to provide specific configurations and
 * tests for the Firefox browser.
 * 
 * @author Ken Kwok
 * @see HeartPageCheck
 */
public class HeartPageCheckforFirefox extends HeartPageCheck {

	/**
	 * Sets up the Firefox browser and navigates to the HKBus application before
	 * each test. It clicks on the Heart Page button after a brief wait.
	 * 
	 * @throws InterruptedException if the thread is interrupted while sleeping
	 */
	@BeforeEach
	@Override
	public void start() throws InterruptedException {
		super.configureBrowser("Firefox", true).get("https://hkbus.app/en");
		Thread.sleep(1000);
		WebElement heartPageButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/a[6]"));
		heartPageButton.click();
		Thread.sleep(3000);
	}

	// All test methods from HeartPageCheck will automatically be included
}