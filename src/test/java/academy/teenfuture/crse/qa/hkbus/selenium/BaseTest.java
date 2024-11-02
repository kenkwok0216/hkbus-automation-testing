package academy.teenfuture.crse.qa.hkbus.selenium;

import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import academy.teenfuture.crse.qa.hkbus.selenium.util.JSONConfigParser;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void start() {
        //driver = configureBrowser("chrome");
    }

    @AfterEach
    public void end() {
        driver.quit();
    }

    protected WebDriver configureBrowser(String browserName) throws JSONException {
        // Configure WebDriver based on browserName
        // String osName = System.getProperty("os.name").toLowerCase();
        String osName = "Mac OS X";
        String driverPath = System.getProperty("user.dir") + JSONConfigParser.getDriverPath(osName, browserName);
        String browserPath = System.getProperty("user.dir") + JSONConfigParser.getBrowserPath(osName, browserName);

		if (osName.contains("win")) {
            // Windows settings
			// Chrome (Win-x86)
			System.setProperty("webdriver.chrome.driver", driverPath);
            ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setBinary(browserPath);
            driver = new ChromeDriver(chromeOptions);

			// Firefox (Win-x86)
			System.setProperty("webdriver.gecko.driver", driverPath);
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setBinary(browserPath);
            driver = new FirefoxDriver(firefoxOptions);

			// Edge (Win-x86)
			System.setProperty("webdriver.edgeDriver.driver", driverPath);
            driver = new EdgeDriver();

        } else if (osName.contains("mac")) {
            // MacOS settings
			// Chrome (MacOS-arm64)
            System.setProperty("webdriver.chrome.driver", driverPath);
			ChromeOptions chromeOptions = new ChromeOptions();
            driver = new ChromeDriver(chromeOptions);

			// Firefox (MacOS-arm64)
			System.setProperty("webdriver.gecko.driver", driverPath);
			FirefoxOptions firefoxOptions = new FirefoxOptions();
            driver = new FirefoxDriver(firefoxOptions);

			// Edge (MacOS-arm64)
            driver = new EdgeDriver();

			// Safari (MacOS-arm64)
            driver = new SafariDriver();

        } else {
            // Handle other OSes or default path

        }
        return driver;
    }
}
