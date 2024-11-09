package academy.teenfuture.crse.qa.hkbus.selenium.settingpage;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * This class extends the SettingPageCheck to provide specific configurations
 * and tests for the Edge browser.
 * 
 * @author Ken Kwok
 * @see SettingPageCheck
 */
public class SettingPageCheckforEdge extends SettingPageCheck {

	/**
	 * Sets up the Edge browser and navigates to the HKBus application before each
	 * test. It clicks on the Heart Page button after a brief wait.
	 * 
	 * @throws InterruptedException if the thread is interrupted while sleeping
	 */
	@BeforeEach
	@Override
	public void start() throws InterruptedException {
		super.configureBrowser("Edge", true).get("https://hkbus.app/en");
		Thread.sleep(1000);
		WebElement heartPageButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/a[6]"));
		heartPageButton.click();
		Thread.sleep(3000);
	}

	// All test methods from SettingPageCheck will automatically be included
}