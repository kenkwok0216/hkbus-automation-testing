package academy.teenfuture.crse.qa.hkbus.selenium.heartpage;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HeartPageCheckforChrome extends HeartPageCheck {

	@BeforeEach
	@Override
	public void start() throws InterruptedException {
		super.configureBrowser("Chrome", true).get("https://hkbus.app/en");
		Thread.sleep(1000);
		WebElement heartPageButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/a[6]"));
		heartPageButton.click();
		Thread.sleep(3000);
	}

	// All test methods from HeartPageCheck will automatically be included
}