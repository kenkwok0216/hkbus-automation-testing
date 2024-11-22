package academy.teenfuture.crse.qa.hkbus.playwright;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * BaseTest is a superclass designed for managing test setup and execution for
 * Playwright-based browser automation tests. It provides a foundation for
 * creating and running automated tests in a consistent environment.
 * 
 * This class includes the following key functionalities:
 * 
 * <ul>
 * <li><b>Browser Management:</b> Initializes and manages browser contexts and
 * pages for each test, ensuring isolated test environments.</li>
 * <li><b>Video Recording:</b> Configures video recording for each test session,
 * enabling users to review test executions for debugging purposes.</li>
 * <li><b>Extent Reports Integration:</b> Facilitates the integration with
 * Extent Reports for generating detailed test reports. This includes logging
 * test results, attaching screenshots, and providing a visual representation of
 * test outcomes.</li>
 * <li><b>Resource Cleanup:</b> Ensures proper cleanup of resources such as
 * browser pages and contexts after each test, preventing memory leaks and
 * ensuring resource availability for subsequent tests.</li>
 * </ul>
 * 
 * <h2>Supported Browsers</h2>
 * <ul>
 * <li>Chrome</li>
 * <li>Firefox</li>
 * <li>Edge</li>
 * <li>WebKit</li>
 * </ul>
 * 
 * <p>
 * Usage: Extend this class to create specific test classes. Call the
 * <code>configure()</code> method in the test's setup phase to initialize the
 * browser and report. Use the <code>generateExtentTest()</code> method to log
 * test results and attach screenshots as needed.
 * </p>
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * public class MyTest extends BaseTest {
 * 	&#64;BeforeEach
 * 	public void setup() {
 * 		super.configure("firefox");
 * 	}
 * 
 * 	&#64;Test
 * 	public void testExample() {
 * 		// Test implementation
 * 	}
 * 
 * 	@AfterEach
 * 	public void end() {
 * 		super.endTest();
 * 	}
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * Author: Ken Kwok
 * </p>
 */
public class BaseTest {

	protected static Playwright playwright;
	protected static BrowserType browserType;
	protected static Page page;
	protected static BrowserContext browserContext;
	protected static boolean isVideoRecording; // Track if video recording is enabled

	private static ExtentReports extent;
	private ExtentTest test;

	/**
	 * Initializes Playwright and configures the browser.
	 * 
	 * @param browserName the name of the browser to launch (e.g., "chrome",
	 *                    "firefox").
	 * @return the Page object for the launched browser.
	 */
	public Page configure(String browserName) {
		return configure(browserName, false);
	}

	/**
	 * Configures the browser for testing, optionally enabling video recording.
	 * 
	 * @param browserName the name of the browser to launch (e.g., "chrome",
	 *                    "firefox").
	 * @param isVideo     whether to enable video recording for the test session.
	 * @return the Page object for the launched browser.
	 * @throws IllegalArgumentException if an unsupported browser is specified.
	 */
	public Page configure(String browserName, boolean isVideo) {
		isVideoRecording = isVideo;

		if (playwright == null) {
			playwright = Playwright.create();
		}

		// Determine browser type
		if (isVideo) {
			System.setProperty("java.awt.headless", "false");
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int width = dimension.width;
			int height = dimension.height;

			// Create a timestamped filename for the video
			LocalDateTime currentDateTime = LocalDateTime.now();
			String dateTimeString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
			String testName = "video_" + dateTimeString;

			// Configure the browser for video recording
			if (browserName.equalsIgnoreCase("chrome")) {
				browserType = playwright.chromium();
			} else if (browserName.equalsIgnoreCase("firefox")) {
				browserType = playwright.firefox();
			} else if (browserName.equalsIgnoreCase("edge") || browserName.equalsIgnoreCase("webkit")) {
				browserType = playwright.webkit();
			} else {
				throw new IllegalArgumentException("Unsupported browser: " + browserName);
			}

			Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
			browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height)
					.setRecordVideoDir(Paths.get("video/")).setRecordVideoSize(width, height));
			page = browserContext.newPage();

			// Add a method to handle video recording
			handleVideoRecording(testName);
		} else {
			// Configure the browser without video recording
			if (browserName.equalsIgnoreCase("chrome")) {
				browserType = playwright.chromium();
			} else if (browserName.equalsIgnoreCase("firefox")) {
				browserType = playwright.firefox();
			} else if (browserName.equalsIgnoreCase("edge") || browserName.equalsIgnoreCase("webkit")) {
				browserType = playwright.webkit();
			} else {
				throw new IllegalArgumentException("Unsupported browser: " + browserName);
			}

			Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
			browserContext = browser.newContext();

			page = browserContext.newPage();
		}

		return page;
	}

	/**
	 * Handles video recording by registering a shutdown hook that processes the
	 * recorded video.
	 * 
	 * @param testName the name used for the recorded video file.
	 */
	private void handleVideoRecording(String testName) {
		// Example usage of the video recording handling
		// This method will run after the program
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (page.video() != null) {
				// Retrieve the name of the video file created by the recording
				Path videoName = page.video().path().getFileName();
				System.out.println("videoName : " + videoName);

				// Close resources
				browserContext.close();
				page.close();
				playwright.close();

				// Locate the original video file and rename it with the timestamped file name
				String pathProject = System.getProperty("user.dir");
				File originalFile = new File(pathProject + File.separator + "video" + File.separator + videoName);
				File renamedFile = new File(
						pathProject + File.separator + "video" + File.separator + testName + ".webm");
				boolean success = originalFile.renameTo(renamedFile);

				if (success) {
					System.out.println("Video renamed to: " + renamedFile.getAbsolutePath());
				} else {
					System.err.println("Failed to rename video file.");
				}

			}
		}));
	}

	/**
	 * Initializes the ExtentReports instance and configures the report settings.
	 */
	private void setupExtentReports() {
		extent = new ExtentReports();
		LocalDateTime currentDateTime = LocalDateTime.now();
		String dateTimeString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
		String path = System.getProperty("user.dir") + "/testresult/index-" + dateTimeString + ".html";

		// Create a new Extent Spark Reporter and configure it
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
	 * Generates a test entry in the Extent Report.
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
	 * Ends the test execution by cleaning up resources.
	 */
	public void endEachTest() {
		// Cleanup resources only if video recording is not enabled
		if (!isVideoRecording) {
			if (page != null) {
				page.close();
				page = null; // Set to null to avoid reuse
			}
			if (browserContext != null) {
				browserContext.close();
				browserContext = null; // Set to null to avoid reuse
			}
			if (playwright != null) {
				playwright.close();
				playwright = null; // Set to null to avoid reuse
			}
		}
	}

	/**
	 * Ends the test execution by flushing reports.
	 */
	public static void endAllTest() {
		// Flush reports and clean up
		flushExtentReports();

	}
}