package academy.teenfuture.crse.qa.hkbus.playwright.heartpage;

import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * The {@code HeartPageCheck} class contains test cases for the Heart Page of
 * the HKBus application. It utilizes the Playwright library to automate browser
 * interactions for testing various functionalities such as emoji selection,
 * noun selection, adjective selection, and submission processes.
 * 
 * This class extends {@link BaseTest} to inherit common testing
 * functionalities.
 *
 * <p>
 * The tests are executed in the following order:
 * <ul>
 * <li>{@code emojiTest}</li>
 * <li>{@code nounTest}</li>
 * <li>{@code adjectiveTest}</li>
 * <li>{@code SubmitButtonTest}</li>
 * <li>{@code SubmissionTest}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Each test case is annotated with {@code @Order(n)} to specify the execution
 * order. The {@code @Disabled} annotation can be used to skip specific tests
 * during execution.
 * </p>
 * 
 * <p>
 * The class includes setup and teardown methods to manage the test environment:
 * <ul>
 * <li>{@code start()} is executed before each test to navigate to the Heart
 * Page.</li>
 * <li>{@code endEach()} is executed after each test to reset the test
 * environment.</li>
 * <li>{@code endAll()} is executed after all tests have run to perform final
 * cleanup.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Note: Tests may include thread sleep statements for synchronization purposes,
 * which can be adjusted based on the application's responsiveness.
 * </p>
 * 
 * @see BaseTest
 * 
 * @author Ken Kwok
 */

import org.junit.jupiter.api.TestMethodOrder;

import com.microsoft.playwright.Locator;

import academy.teenfuture.crse.qa.hkbus.playwright.BaseTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HeartPageTest extends BaseTest {

	/**
	 * Sets up the test environment before each test. This method navigates to the
	 * Heart Page of the HKBus application and ensures the necessary UI elements are
	 * ready for interaction.
	 *
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	@BeforeEach
	public void start() throws InterruptedException {
		// super.configure("Chrome").navigate("https://www.google.com");
		super.configure("firefox", false).navigate("https://hkbus.app/en");
		// Locate button to Heart Page and click it
		Locator HeartPage = page.locator("//*[@id=\"root\"]/div/div[3]/a[6]");
		HeartPage.click();
		Thread.sleep(1000);
	}

	/**
	 * Tests the selection of emoji options on the Heart Page.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	@Test
	@Order(1)
	// @Disabled
	public void emojiTest() throws InterruptedException, IOException {
		testSelection("Test for emojis", "(//div[@role='group'])[1]", 1);
	}

	/**
	 * Tests the selection of noun options on the Heart Page.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	@Test
	@Order(2)
	// @Disabled
	public void nounTest() throws InterruptedException, IOException {
		testSelection("Test for nouns", "(//div[@role='group'])[2]", 2);
	}

	/**
	 * Tests the selection of adjective options on the Heart Page.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	@Test
	@Order(3)
	// @Disabled
	public void adjectiveTest() throws InterruptedException, IOException {
		testSelection("Test for adjectives", "(//div[@role='group'])[3]", 3);
	}

	/**
	 * Tests the behavior of the 'OK' button after all questions are answered. This
	 * test is currently disabled.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	@Test
	@Order(4)
	@Disabled
	public void SubmitButtonTest() throws InterruptedException, IOException {
		// Test Name of the method
		String testName = "Test for OK button";

		// Variable tracking is all question is answered
		boolean isAnsweredAllQuestion = false;

		// Locator the option lines
		Locator emojiLocator = page.locator("(//div[@role='group'])[1]");
		Locator nounLocator = page.locator("(//div[@role='group'])[2]");
		Locator adjectiveLocator = page.locator("(//div[@role='group'])[3]");

		// Locator all the button in the adjectives lines
		Locator emojis = emojiLocator.locator("button");
		Locator nouns = nounLocator.locator("button");
		Locator adjectives = adjectiveLocator.locator("button");

		Locator[] questions = { emojis, nouns, adjectives };

		// No Question is answered
		updateOkButtonState(isAnsweredAllQuestion, testName);
		Thread.sleep(2000);

		randomAnswerQuestion(questions[0], isAnsweredAllQuestion, testName);

		randomAnswerQuestion(questions[0], questions[1], isAnsweredAllQuestion, testName);
		randomAnswerQuestion(questions[0], questions[2], isAnsweredAllQuestion, testName);
		randomAnswerQuestion(questions[1], questions[2], isAnsweredAllQuestion, testName);

		isAnsweredAllQuestion = true;
		randomAnswerQuestion(questions[0], questions[1], questions[2], isAnsweredAllQuestion, testName);

	}

	/**
	 * Tests the overall submission functionality of the Heart Page. This test is
	 * currently disabled.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	@Test
	@Order(5)
	@Disabled
	public void SubmissionTest() throws InterruptedException, IOException {
		// Test Name of the method
		String testName = "Test for submission";

		// Locator the option lines
		Locator emojiLocator = page.locator("(//div[@role='group'])[1]");
		Locator nounLocator = page.locator("(//div[@role='group'])[2]");
		Locator adjectiveLocator = page.locator("(//div[@role='group'])[3]");

		// Locator all the button in the adjectives lines
		Locator emojis = emojiLocator.locator("button");
		Locator nouns = nounLocator.locator("button");
		Locator adjectives = adjectiveLocator.locator("button");

		Random rnd = new Random();

		Locator[] questions = { emojis, nouns, adjectives };

		for (int i = 0; i < 3; i++) {
			questions[i].nth(rnd.nextInt(questions[i].count())).click();
			Thread.sleep(3000);
		}

		Locator button = page.locator("//button[normalize-space(text())='OK']");

		button.click();
		Thread.sleep(3000);

		Locator checkin = page.locator("(//div[@class='MuiBox-root hkbus-1594xsb']//h6)[1]");
		if (checkin.textContent().contains("I have recorded your mood")) {
			generateExtentTest(testName, true, "This test case is pass", page.screenshot());
		} else {
			generateExtentTest(testName, false, "There are something wrong in this test", page.screenshot());
		}

	}

	/**
	 * Cleans up after each test, ensuring the test environment is properly reset.
	 * This method is called after each test execution to allow for any necessary
	 * cleanup or state resets.
	 *
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	@AfterEach
	public void endEach() throws InterruptedException {
		Thread.sleep(1000);
		endEachTest();
	}

	/**
	 * Final cleanup after all tests have executed. This method is called after all
	 * tests have completed to perform any final cleanup or resource release.
	 *
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	@AfterAll
	public static void endAll() throws InterruptedException {
		Thread.sleep(1000);
		endAllTest();
	}

	/**
	 * Executes a selection test based on provided parameters.
	 * 
	 * @param testName          the name of the test being executed.
	 * @param locatorExpression the XPath expression to locate the options.
	 * @param groupIndex        the index of the group in the DOM.
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	private void testSelection(String testName, String locatorExpression, int groupIndex)
			throws InterruptedException, IOException {
		// Locator the option lines
		Locator optionLocator = page.locator(locatorExpression);
		Thread.sleep(1000);

		// Locator all the options in the lines
		Locator options = optionLocator.locator("button");
		Thread.sleep(1000);

		// Get the number of buttons
		int count = options.count();

		// Boolean to track test success
		boolean error = false;

		// Loop through each button and click it
		for (int i = 0; i < count; i++) {
			try {
				Locator option = options.nth(i);
				option.click();
				Thread.sleep(1000);

				// Check if the button is clicked
				String ariaPressed = option.getAttribute("aria-pressed");
				if (ariaPressed.equals("false")) {
					throw new Exception("The button cannot be selected");
				}

				for (int j = 0; j < count; j++) {
					// Check whether other buttons are de-selected
					if (j != i) {
						Locator otherOption = options.nth(j);
						String otherAriaPressed = otherOption.getAttribute("aria-pressed");
						if (otherAriaPressed.equals("true")) {
							throw new Exception("The other buttons are not de-selected");
						}
					}
				}

			} catch (Exception e) {
				byte[] screenshot = page.locator("(//div[@class='MuiBox-root hkbus-srxn8'])[" + groupIndex + "]")
						.screenshot();
				super.generateExtentTest(testName, false, e.getMessage(), screenshot);
				error = true;
			}
		}

		// If there are no errors, pass the test
		if (!error) {
			byte[] screenshot = page.locator("(//div[@class='MuiBox-root hkbus-srxn8'])[" + groupIndex + "]")
					.screenshot();
			super.generateExtentTest(testName, true, "This test case is passed");
		}
	}

	/**
	 * Updates the state of the 'OK' button based on whether all questions are
	 * answered.
	 * 
	 * @param isAnsweredAllQuestion boolean indicating if all questions are
	 *                              answered.
	 * @param testName              the name of the test for logging purposes.
	 * @throws IOException if an I/O error occurs during test execution.
	 */
	private void updateOkButtonState(boolean isAnsweredAllQuestion, String testName) throws IOException {
		try {

			Locator buttonState = page.locator("//button[normalize-space(text())='OK']");

			// Check whether the disabled
			String buttonStateAttribute = buttonState.getAttribute("disabled");

			if (isAnsweredAllQuestion == false) {
				if (buttonStateAttribute == null) {
					throw new Exception("The button should not be enabled is this stage.");
				}
			} else {
				if (buttonStateAttribute != null) {
					throw new Exception("The button be enabled is this stage.");
				}
			}

		} catch (Exception e) {

			generateExtentTest(testName, false, e.getMessage(), page.screenshot());

		} finally {
			if (isAnsweredAllQuestion) {
				generateExtentTest(testName, true, "This test case is pass", page.screenshot());
			}
		}
	}

	/**
	 * Randomly answers a question based on the provided locator.
	 * 
	 * @param answer1               the locator for the first answer.
	 * @param isAnsweredAllQuestion boolean indicating if all questions are
	 *                              answered.
	 * @param testName              the name of the test for logging purposes.
	 * @throws IOException          if an I/O error occurs during test execution.
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	private void randomAnswerQuestion(Locator answer1, boolean isAnsweredAllQuestion, String testName)
			throws IOException, InterruptedException {
		// Create a random object for random answer for each question
		Random rnd = new Random();

		// Create Random number
		int x = rnd.nextInt(answer1.count());

		// Click the button
		answer1.nth(x).click();

		updateOkButtonState(isAnsweredAllQuestion, testName);

		Thread.sleep(2000);

		answer1.nth(x).click();

		Thread.sleep(2000);

	}

	/**
	 * Randomly answers two questions based on the provided locators.
	 * 
	 * @param answer1               the locator for the first answer.
	 * @param answer2               the locator for the second answer.
	 * @param isAnsweredAllQuestion boolean indicating if all questions are
	 *                              answered.
	 * @param testName              the name of the test for logging purposes.
	 * @throws IOException          if an I/O error occurs during test execution.
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	private void randomAnswerQuestion(Locator answer1, Locator answer2, boolean isAnsweredAllQuestion, String testName)
			throws IOException, InterruptedException {
		// Create a random object for random answer for each question
		Random rnd = new Random();

		// Create Random number
		int x = rnd.nextInt(answer1.count());
		int y = rnd.nextInt(answer2.count());

		// Click the button
		answer1.nth(x).click();
		Thread.sleep(1000);
		answer2.nth(y).click();

		updateOkButtonState(isAnsweredAllQuestion, testName);

		Thread.sleep(2000);

		// unselect the button
		answer1.nth(x).click();
		Thread.sleep(1000);
		answer2.nth(y).click();

		Thread.sleep(2000);
	}

	/**
	 * Randomly answers three questions based on the provided locators.
	 * 
	 * @param answer1               the locator for the first answer.
	 * @param answer2               the locator for the second answer.
	 * @param answer3               the locator for the third answer.
	 * @param isAnsweredAllQuestion boolean indicating if all questions are
	 *                              answered.
	 * @param testName              the name of the test for logging purposes.
	 * @throws IOException          if an I/O error occurs during test execution.
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	private void randomAnswerQuestion(Locator answer1, Locator answer2, Locator answer3, boolean isAnsweredAllQuestion,
			String testName) throws IOException, InterruptedException {
		// Create a random object for random answer for each question
		Random rnd = new Random();

		// Create Random number
		int x = rnd.nextInt(answer1.count());
		int y = rnd.nextInt(answer2.count());
		int z = rnd.nextInt(answer3.count());

		// Click the button
		answer1.nth(x).click();
		Thread.sleep(1000);
		answer2.nth(y).click();
		Thread.sleep(1000);
		answer3.nth(z).click();

		updateOkButtonState(isAnsweredAllQuestion, testName);

		Thread.sleep(2000);

		// unselect the buttons
		answer1.nth(x).click();
		Thread.sleep(1000);
		answer2.nth(y).click();
		Thread.sleep(1000);
		answer3.nth(z).click();

		Thread.sleep(2000);
	}

}
