package academy.teenfuture.crse.qa.hkbus.selenium;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariDriver;

import academy.teenfuture.crse.qa.hkbus.selenium.util.JSONConfigParser;

public class BaseTest {

    protected WebDriver driver;
    protected ChromeOptions chromeOptions;
    protected FirefoxOptions firefoxOptions;

    @BeforeEach
    public void start() {
        //driver = configureBrowser("chrome");
    }

    @AfterEach
    public void end() {
        driver.quit();
    }

    /**
     * 
     * @param browserName "Chrome", "Firefox", "Edge", "Safari"
     * @return
     * @throws JSONException
     */
    protected WebDriver configureBrowser(String browserName) {
        // Configure WebDriver based on browserName
        String osName = null;
        String driverReletivePath = null;
        String driverPath = null;
        String browserPath = null;

        osName = System.getProperty("os.name").toLowerCase();

        // Convert osName
        if (osName.contains("win")) {
            osName = "windows_x86";
        } else if (osName.contains("mac")) {
            osName = "mac_aarch64";
        } else {
            // Handle other OSes or default path

        }

        // Get driver path from config.json file
        driverReletivePath = JSONConfigParser.getDriverPath(osName, browserName.toLowerCase());
        driverPath = System.getProperty("user.dir") + driverReletivePath;
        browserPath = JSONConfigParser.getBrowserPath(osName, browserName.toLowerCase());
        
		if (osName.contains("win")) {
            // Windows settings
			// Chrome (Win-x86)
			System.setProperty("webdriver.chrome.driver", driverPath);

			// Firefox (Win-x86)
			System.setProperty("webdriver.gecko.driver", driverPath);

			// Edge (Win-x86)
			System.setProperty("webdriver.edgeDriver.driver", driverPath);

            // Finally
            this.driver = createWebDriver(browserName, browserPath);

        } else if (osName.contains("mac")) {
            // MacOS settings
			// Chrome (MacOS-arm64)
            System.setProperty("webdriver.chrome.driver", driverPath);


			// Firefox (MacOS-arm64)
			System.setProperty("webdriver.gecko.driver", driverPath);


			// Edge (MacOS-arm64)


			// Safari (MacOS-arm64)

            // Finally
            this.driver = createWebDriver(browserName);
        } else {
            // Handle other OSes or default path
            this.driver = null;
        }
        
        return this.driver;
    }

    /**
     * Use reflection to create WebDriver instance
     * @param browserName Browser name in Pascal case, e.g. "Chrome", "Firefox", "Edge", "Safari"
     * @return instance of corresponding WebDriver
     */
    private WebDriver createWebDriver(String browserName) {
        WebDriver driver = null;
        try {
            Class<?> driverClass = Class.forName("org.openqa.selenium." + browserName.toLowerCase() + "." + browserName + "Driver");
            Constructor<?> driverConstructor = driverClass.getConstructor();
            driver = (WebDriver) driverConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    private WebDriver createWebDriver(String browserName, String browserPath) {
        WebDriver driver = null;
        try {
            Class<?> optionsClass = Class.forName("org.openqa.selenium." + browserName.toLowerCase() + "." + browserName + "Options");
            Class<?> driverClass = Class.forName("org.openqa.selenium." + browserName.toLowerCase() + "." + browserName + "Driver");

            Constructor<?> optionsConstructor = optionsClass.getConstructor();
            Object optionsInstance = optionsConstructor.newInstance();

            Method setBinaryMethod = optionsInstance.getClass().getMethod("setBinary", String.class);
            setBinaryMethod.invoke(optionsInstance, browserPath);

            Constructor<?> driverConstructor = driverClass.getConstructor(optionsClass);
            driver = (WebDriver) driverConstructor.newInstance(optionsInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }
}
