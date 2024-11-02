package academy.teenfuture.crse.Utility;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class SeleniumTests {
	// Set up of Firefox
	@Disabled
	@Test
	public void getStart_Firefox() {
		System.setProperty("webdriver.gecko.driver",
				System.getProperty("user.dir") + "\\src\\main\\resources\\Driver\\FirefoxDriver\\geckodriver.exe");

		FirefoxOptions options = new FirefoxOptions();
		options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

		WebDriver driver = new FirefoxDriver(options);
		// WevDriver driver = new FirefoxDriver(); //for default setting
		driver.get("https://www.google.com.hk");
		driver.close();
	}

	// Set up of Chrome
	// Waiting update for selenium
	@Disabled
	@Test
	public void getStart_Chrome() {
		System.setProperty("webdriver.chrome.driver",
				// should set your driver path in String, eg.
				// "C://AutoTestGraspScrapeCrawl//selenium//Driver//chromedriver.exe"
				System.getProperty("user.dir") + "\\src\\main\\resources\\driver\\ChromeDriver\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

		WebDriver driver = new ChromeDriver(options);
		// WebDriver driver = new ChromeDriver(); //for default path
		driver.get("https://www.google.com.hk");
		driver.close();
	}

	@Disabled
	@Test
	public void getStart_Edge() {
		// should set your driver path in String, eg.
		// "C://AutoTestGraspScrapeCrawl//selenium//driver//msedgedriver.exe"
		System.setProperty("webdriver.edgeDriver.driver",
				System.getProperty("user.dir") + "\\src\\main\\resources\\driver\\EdgeDriver\\chromedriver.exe");

		WebDriver driver = new EdgeDriver();

		driver.get("https://www.google.com.hk");
		driver.close();
	}

	@Disabled
	@Test
	public void getStart_Safari() {
		WebDriver driver = new SafariDriver();
		driver.get("https://www.google.com.hk");
		driver.close();
	}
}
