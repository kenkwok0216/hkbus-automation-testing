package academy.teenfuture.crse.qa.hkbus.selenium;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import academy.teenfuture.crse.qa.hkbus.selenium.util.JSONConfigParser;
import academy.teenfuture.crse.qa.hkbus.selenium.util.ScreenRecorderUtil;

/**
 * This is the superclass of other test classes, it can load the drivers and the
 * browsers from the config.json file.
 * 
 * @author Franck Cheng
 */
public class BaseTest {

	// Store the browser name and map it to System.setProperty() key
	private static final Map<String, String> propertyMap = new HashMap<>();

	protected WebDriver driver;

	private boolean isRecording; // Flag to track if recording is active
	private static ExtentReports extent;
	private ExtentTest test;

	static {
		propertyMap.put("Chrome", "webdriver.chrome.driver");
		propertyMap.put("Firefox", "webdriver.gecko.driver");
		propertyMap.put("Edge", "webdriver.edgeDriver.driver");
	}

	/**
	 * This method will load the paths from config.json file base on browser name
	 * and provide a WebDriver
	 * 
	 * @param browserName Browser name in Pascal case, e.g. "Chrome", "Firefox",
	 *                    "Edge", "Safari"
	 * @return Instance of corresponding WebDriver
	 */
	protected WebDriver configureBrowser(String browserName) {
		return configureBrowser(browserName, false);
	}

	/**
	 * Configures the WebDriver instance based on the specified browser name and
	 * video recording preference. This method sets up the browser environment,
	 * prepares the WebDriver for use, and optionally starts video recording based
	 * on the provided flag.
	 *
	 * @param browserName The name of the browser to configure (e.g., "Chrome",
	 *                    "Firefox").
	 * @param isVideo     A boolean flag indicating whether to start video recording
	 *                    (true to start recording).
	 * @return An instance of the corresponding WebDriver for the specified browser.
	 * @throws RuntimeException if the WebDriver cannot be created for the specified
	 *                          browser or if there are issues with starting the
	 *                          screen recording.
	 */
	protected WebDriver configureBrowser(String browserName, boolean isVideo) {
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

		// Start video recording if isVideo is true
		if (isVideo) {
			try {
				ScreenRecorderUtil.startRecord("testRecord");
				isRecording = true; // Set the flag to true
			} catch (Exception e) {
				System.err.println("Failed to start video recording: " + e.getMessage());
			}
		}

		// If the browser path exists in the config file, and the file exists, set
		// specific location, otherwise use default
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
	 * @param browserName Browser name in Pascal case, e.g. "Chrome", "Firefox",
	 *                    "Edge", "Safari"
	 * @return Instance of corresponding WebDriver
	 */
	private WebDriver createWebDriver(String browserName) {
		WebDriver driver = null;
		try {
			Class<?> driverClass = Class
					.forName("org.openqa.selenium." + browserName.toLowerCase() + "." + browserName + "Driver");
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
	 * @param browserName Browser name in Pascal case, e.g. "Chrome", "Firefox",
	 *                    "Edge", "Safari"
	 * @param browserPath Absolute path of browser executable
	 * @return Instance of corresponding WebDriver
	 */
	private WebDriver createWebDriver(String browserName, String browserPath) {
		WebDriver driver = null;
		try {
			Class<?> optionsClass = Class
					.forName("org.openqa.selenium." + browserName.toLowerCase() + "." + browserName + "Options");
			Class<?> driverClass = Class
					.forName("org.openqa.selenium." + browserName.toLowerCase() + "." + browserName + "Driver");

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

	/**
	 * Quits the WebDriver instance and releases any associated resources. This
	 * method ensures that the browser is closed and any video recording, if
	 * started, is stopped properly.
	 *
	 * @throws RuntimeException if an error occurs while stopping the video
	 *                          recording.
	 */
	public void quitDriver() {
		if (driver != null) {
			driver.quit();
		}
		// Stop video recording only if it was started
		if (isRecording) {
			try {
				ScreenRecorderUtil.stopRecord();
			} catch (Exception e) {
				System.err.println("Failed to stop video recording: " + e.getMessage());
			}
		}
	}

	/**
	 * Flushes the ExtentReports instance, writing all report data to the output
	 * file. This method should be called after all tests have been executed to
	 * ensure that the report contains the latest results and is properly saved.
	 */
	public static void endAllTest() {
		flushExtentReports(); // Flush the ExtentReports instance to save the report
	}

	/**
	 * Initializes the ExtentReports instance and configures the report settings.
	 */
	private void setupExtentReports() {
		extent = new ExtentReports();
		LocalDateTime currentDateTime = LocalDateTime.now();
		String dateTimeString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
		String path = System.getProperty("user.dir") + "/testresult/index-" + dateTimeString + ".html";

		ExtentSparkReporter spark = new ExtentSparkReporter(path);
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setDocumentTitle("Automation Testing Report");
		spark.config().setReportName("Extent Report Sample");
		extent.attachReporter(spark);
	}

	/**
	 * Generates a test entry in the Extent Report.
	 * 
	 * @param testName  the name of the test case to log.
	 * @param isSuccess whether the test case passed or failed.
	 * @param message   a message describing the test case result.
	 */
	public void generateExtentTest(String testName, boolean isSuccess, String message) {
		if (extent == null) {
			setupExtentReports();
		}
		test = extent.createTest(testName);
		if (isSuccess) {
			test.pass(message);
		} else {
			test.fail(MarkupHelper.createLabel(message, ExtentColor.RED));
		}
	}

	/**
	 * Generates a test entry in the Extent Report with a screenshot.
	 * 
	 * @param testName   the name of the test case to log.
	 * @param isSuccess  whether the test case passed or failed.
	 * @param message    a message describing the test case result.
	 * @param screenshot a byte array representing the screenshot to attach to the
	 *                   report.
	 * @throws IOException if an error occurs while processing the screenshot.
	 */
	public void generateExtentTest(String testName, boolean isSuccess, String message, byte[] screenshot)
			throws IOException {
		if (extent == null) {
			setupExtentReports();
		}

		test = extent.createTest(testName);

		if (isSuccess) {
			String base64Image = Base64.getEncoder().encodeToString(screenshot);
			test.pass(message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
		} else {
			String base64Image = Base64.getEncoder().encodeToString(screenshot);
			test.fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
		}
	}

	/**
	 * Flushes the ExtentReports instance, writing all report data to the output
	 * file.
	 */
	public static void flushExtentReports() {
		if (extent != null) {
			extent.flush();
		}
	}

	/**
	 * Captures a screenshot and returns it as a byte array.
	 *
	 * @return byte array of the screenshot.
	 */
	protected byte[] captureScreenshot() {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			return ts.getScreenshotAs(OutputType.BYTES);
		} catch (WebDriverException e) {
			System.err.println("Failed to capture screenshot: " + e.getMessage());
			return new byte[0]; // Return an empty byte array if screenshot fails
		}
	}

	/**
	 * This method will capture and save the screenshot to default directory
	 */
	protected void captureAndSaveScreenshot() {
		String dir;

		if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
			// Windows path
			dir = "\\screenshots\\selenium";
		} else {
			// Other OS path
			dir = "/screenshots/selenium";
		}

        File directoryFile = new File(System.getProperty("user.dir") + dir);
		// Create directory if not exist
        if (!directoryFile.exists()) {
            boolean success = directoryFile.mkdirs();
            if (!success) {
                System.out.println("Failed to create directory: " + dir);
            }
        }

		captureAndSaveScreenshot(dir);
	}

	/**
	 * This method will capture and save the screenshot to the given directory
	 * 
	 * @param dir The directory to save screenshot, please make sure to manual create the directory before using it
	 */
	protected void captureAndSaveScreenshot(String dir) {
		// Generate a timestamp for the screenshot file name
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
		String dirPath = System.getProperty("user.dir") + dir;

		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);

        // Specify the destination file path
        String fileName = "screenshot_" + timestamp + ".png";
        File destFile = new File(dirPath, fileName);

        // Copy the screenshot file to the destination
        try {
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

