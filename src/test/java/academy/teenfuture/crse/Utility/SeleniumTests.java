package academy.teenfuture.crse.Utility;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Simple tests for opening different browsers
 * 
 * Last modified: 2024-11-2 2:56 PM
 *
 * @author Franck
 */
@Disabled
public class SeleniumTests {

	// Chrome paths
	public static final String PATH_CHROME_DRIVER_WIN_X86 = "\\src\\main\\resources\\driver\\ChromeDriver\\chromedriver.exe";
	public static final String PATH_CHROME_DRIVER_MAC_ARM64 = "/src/main/resources/Driver/ChromeDriver/mac-arm64/chromedriver";
	public static final String PATH_CHROME_APP_WIN_X86 = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
	public static ChromeOptions chromeOptions;

	// Firefox paths
	public static final String PATH_FIREFOX_DRIVER_WIN_X86 = "\\src\\main\\resources\\Driver\\FirefoxDriver\\geckodriver.exe";
	public static final String PATH_FIREFOX_DRIVER_MAC_ARM64 = "/src/main/resources/Driver/FirefoxDriver/mac-arm64/geckodriver";
	public static final String PATH_FIREFOX_APP_WIN_X86 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	public static FirefoxOptions firefoxOptions;

	// Edge paths
	public static final String PATH_EDGE_DRIVER_WIN_X86 = "\\src\\main\\resources\\driver\\EdgeDriver\\chromedriver.exe";

	@BeforeAll
	public static void osDetectionAndPathAdjustment() {
		String osName = System.getProperty("os.name").toLowerCase();

		if (osName.contains("win")) {
            // Windows settings
			// Chrome (Win-x86)
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + PATH_CHROME_DRIVER_WIN_X86);
            chromeOptions = new ChromeOptions();
			chromeOptions.setBinary(PATH_CHROME_APP_WIN_X86);

			// Firefox (Win-x86)
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + PATH_FIREFOX_DRIVER_WIN_X86);
			firefoxOptions = new FirefoxOptions();
			firefoxOptions.setBinary(PATH_FIREFOX_APP_WIN_X86);

			// Edge (Win-x86)
			System.setProperty("webdriver.edgeDriver.driver", System.getProperty("user.dir") + PATH_EDGE_DRIVER_WIN_X86);

        } else if (osName.contains("mac")) {
            // MacOS settings
			// Chrome (MacOS-arm64)
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + PATH_CHROME_DRIVER_MAC_ARM64);
			chromeOptions = new ChromeOptions();

			// Firefox (MacOS-arm64)
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + PATH_FIREFOX_DRIVER_MAC_ARM64);
			firefoxOptions = new FirefoxOptions();

			// Edge (MacOS-arm64)

			// Safari (MacOS-arm64)

        } else {
            // Handle other OSes or default path

        }
	}

	// Set up of Firefox
	@Disabled
	@Test
	public void getStart_Firefox() {
		WebDriver driver = new FirefoxDriver(firefoxOptions);
		// WevDriver driver = new FirefoxDriver(); //for default setting
		driver.get("https://www.google.com.hk");
		driver.close();
	}

	// Set up of Chrome
	// Waiting update for selenium
	@Disabled
	@Test
	public void getStart_Chrome() {
		WebDriver driver = new ChromeDriver(chromeOptions);
		// WebDriver driver = new ChromeDriver(); //for default path
		driver.get("https://www.google.com.hk");
		driver.close();
	}

	@Disabled
	@Test
	public void getStart_Edge() {
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
