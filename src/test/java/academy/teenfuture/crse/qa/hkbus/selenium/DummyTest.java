package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Template test class
 */
public class DummyTest extends BaseTest {

	@BeforeEach
	public void start() {
		super.configureBrowser("Firefox").get("https://www.google.com.hk");
	}

	@Test
	public void simpleDummyTest() {
		String testName = "Simple Dummy Test";
		boolean isSuccess = false;

		try {
			// Your test code here
			String pageTitle = driver.getTitle();
			assert pageTitle.equals("Google");
			isSuccess = true; // Set to true if the test passes
			byte[] screenshot = captureScreenshot();
			generateExtentTest(testName, isSuccess, "Test passed successfully.", screenshot);
		} catch (Exception e) {
			generateExtentTest(testName, isSuccess, "Test failed: " + e.getMessage());
		}
	}

	@AfterEach
	public void end() {
		quitDriver();
	}

}
