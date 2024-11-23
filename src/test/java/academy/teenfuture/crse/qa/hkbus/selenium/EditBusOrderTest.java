package academy.teenfuture.crse.qa.hkbus.selenium;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//1. check default > choose another one // get word

public class EditBusOrderTest extends BaseTest {

	@BeforeEach
	public void start() {
		super.configureBrowser("Chrome").get("https://hkbus.app/zh/board");
	}

	@Test
	@Disabled
	public void editBusOrder() throws InterruptedException {
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// go to settingPage
		WebElement settingPage = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[2]/div/div[1]/div[3]/a")));
		settingPage.click();
		Thread.sleep(2000);

		// click option
		WebElement option = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[2]/div/div[2]/div/ul/div[6]")));
		option.click();
		Thread.sleep(2000);

	}

	@AfterEach
	public void end() {
		quitDriver();
	}

	@AfterAll
	public static void endAll() {
		endAllTest();
	}
}