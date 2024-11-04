package academy.teenfuture.crse.qa.hkbus.selenium;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import academy.teenfuture.crse.qa.hkbus.selenium.util.JSONConfigParser;

/**
 * This is the superclass of other test classes, it can load 
 * the drivers and the browsers from the config.json file. 
 * 
 * @author Franck Cheng
 */
public class BaseTest {

    // Store the browser name and map it to System.setProperty() key
    private static final Map<String, String> propertyMap = new HashMap<>();

    protected WebDriver driver;

    static {
        propertyMap.put("Chrome", "webdriver.chrome.driver");
        propertyMap.put("Firefox", "webdriver.gecko.driver");
        propertyMap.put("Edge", "webdriver.edgeDriver.driver");
    }

    /**
     * This method will load the paths from config.json file base on browser name and provide a WebDriver
     * 
     * @param browserName Browser name in Pascal case, e.g. "Chrome", "Firefox", "Edge", "Safari"
     * @return Instance of corresponding WebDriver
     */
    protected WebDriver configureBrowser(String browserName) {
        // Configure WebDriver based on browserName
        String osName = null;
        String platformName = null;
        String driverRelativePath = null;
        String driverPath = null;
        String browserPath = null;

        osName = System.getProperty("os.name").toLowerCase();

        // Convert osName to match the platform name in the config.json file
        if (osName.startsWith("win")) {
            platformName = "windows_x64";
        } else if (osName.startsWith("mac")) {
            platformName = "macos_aarch64";
        } else {
            // Handle other OSes or default path

        }

        // Get driver path from config.json file
        driverRelativePath = JSONConfigParser.getDriverPath(platformName, browserName.toLowerCase());
        driverPath = System.getProperty("user.dir") + driverRelativePath;
        browserPath = JSONConfigParser.getBrowserPath(platformName, browserName.toLowerCase());
        
        // If the driver path exists in the config file, then setProperty
        if (driverRelativePath != null) {
            System.setProperty(propertyMap.get(browserName), driverPath);
        }

        // If the browser path exists in the config file, and the file exists, set specific location, otherwise use default
        if (browserPath != null && new File(browserPath).exists()) {
            this.driver = createWebDriver(browserName, browserPath);
        } else {
            this.driver = createWebDriver(browserName);
        }
        
        return this.driver;
    }

    /**
     * Use reflection to create WebDriver instance
     * 
     * @param browserName Browser name in Pascal case, e.g. "Chrome", "Firefox", "Edge", "Safari"
     * @return Instance of corresponding WebDriver
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
    
    /**
     * Use reflection to create WebDriver instance from specified browser location
     * 
     * @param browserName Browser name in Pascal case, e.g. "Chrome", "Firefox", "Edge", "Safari"
     * @param browserPath Absolute path of browser executable
     * @return Instance of corresponding WebDriver
     */
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
