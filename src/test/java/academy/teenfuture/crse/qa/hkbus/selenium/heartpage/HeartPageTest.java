package academy.teenfuture.crse.qa.hkbus.selenium.heartpage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import academy.teenfuture.crse.qa.hkbus.selenium.BaseTest;

/**
 * The {@code HeartPageCheck} class contains test cases for the Heart Page of
 * the HKBus application. It utilizes the Selenium library to automate browser
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
 * <li>{@code end()} is executed after each test to reset the test
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
 * <p>
 * The class contains several helper methods:
 * <ul>
 * <li>{@code testSelection(String testName, String locatorExpression, int groupIndex)}:
 * Verifies the selection behavior of buttons within a specific group.</li>
 * <li>{@code updateOkButtonState(boolean isAnsweredAllQuestion, String testName)}:
 * Checks the enabled/disabled state of the 'OK' button based on answered
 * questions.</li>
 * <li>{@code randomAnswerQuestion(...)}`: Overloaded methods to randomly answer
 * questions from one or more button groups.</li>
 * </ul>
 * </p>
 * 
 * @see BaseTest
 * 
 * @author Ken Kwok
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HeartPageTest extends BaseTest {
	/**
	 * Sets up the test environment by navigating to the Heart Page of the HKBus
	 * application. This method is executed before each test.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	@BeforeEach
	public void start() throws InterruptedException {
		super.configureBrowser("Firefox", true).get("https://hkbus.app/en");
		Thread.sleep(1000);
		WebElement heartPageButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/a[6]"));
		heartPageButton.click();
		Thread.sleep(3000);
	}

	/**
	 * Tests the selection of emojis on the Heart Page. This test is currently
	 * disabled.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	@Test
	@Order(1)
	@Disabled
	public void emojiTest() throws InterruptedException, IOException {
		testSelection("Test for emojis", "(//div[@role='group'])[1]", 1);
	}

	/**
	 * Tests the selection of nouns on the Heart Page. This test is currently
	 * disabled.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	@Test
	@Order(2)
	@Disabled
	public void nounTest() throws InterruptedException, IOException {
		testSelection("Test for nouns", "(//div[@role='group'])[2]", 2);
	}

	/**
	 * Tests the selection of adjectives on the Heart Page. This test is currently
	 * disabled.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 * @throws IOException          if an I/O error occurs during test execution.
	 */
	@Test
	@Order(3)
	@Disabled
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

		// Locate the option lines
		WebElement emojiLocator = driver.findElement(By.xpath("(//div[@role='group'])[1]"));
		WebElement nounLocator = driver.findElement(By.xpath("(//div[@role='group'])[2]"));
		WebElement adjectiveLocator = driver.findElement(By.xpath("(//div[@role='group'])[3]"));

		// Locate all the buttons in the option lines
		List<WebElement> emojis = emojiLocator.findElements(By.tagName("button"));
		List<WebElement> nouns = nounLocator.findElements(By.tagName("button"));
		List<WebElement> adjectives = adjectiveLocator.findElements(By.tagName("button"));

		// Create a list to hold all questions
		List<List<WebElement>> questions = new ArrayList<>();
		questions.add(emojis);
		questions.add(nouns);
		questions.add(adjectives);

		// No Question is answered
		updateOkButtonState(isAnsweredAllQuestion, testName);
		Thread.sleep(2000);

		randomAnswerQuestion(questions.get(0), isAnsweredAllQuestion, testName);

		randomAnswerQuestion(questions.get(0), questions.get(1), isAnsweredAllQuestion, testName);
		randomAnswerQuestion(questions.get(0), questions.get(2), isAnsweredAllQuestion, testName);
		randomAnswerQuestion(questions.get(1), questions.get(2), isAnsweredAllQuestion, testName);

		isAnsweredAllQuestion = true;
		randomAnswerQuestion(questions.get(0), questions.get(1), questions.get(2), isAnsweredAllQuestion, testName);

	}

	/**
	 * Tests the submission process on the Heart Page. This test is currently
	 * disabled.
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
		WebElement emojiLocator = driver.findElement(By.xpath("(//div[@role='group'])[1]"));
		WebElement nounLocator = driver.findElement(By.xpath("(//div[@role='group'])[2]"));
		WebElement adjectiveLocator = driver.findElement(By.xpath("(//div[@role='group'])[3]"));

		// Locator all the button in the adjectives lines
		List<WebElement> emojis = emojiLocator.findElements(By.tagName("button"));
		List<WebElement> nouns = nounLocator.findElements(By.tagName("button"));
		List<WebElement> adjectives = adjectiveLocator.findElements(By.tagName("button"));

		Random rnd = new Random();

		// Array of question buttons
		List<List<WebElement>> questions = List.of(emojis, nouns, adjectives);

		// Randomly select one button from each category
		for (List<WebElement> question : questions) {
			int randomIndex = rnd.nextInt(question.size());
			question.get(randomIndex).click();
			Thread.sleep(3000); // Wait for 3 seconds after each click
		}

		// Locate and click the OK button
		WebElement button = driver.findElement(By.xpath("//button[normalize-space(text())='OK']"));
		button.click();
		Thread.sleep(3000); // Wait for 3 seconds after clicking OK

		// Check the result
		WebElement checkin = driver.findElement(By.xpath("(//div[@class='MuiBox-root hkbus-1594xsb']//h6)[1]"));
		if (checkin.getText().contains("I have recorded your mood")) {
			generateExtentTest(testName, true, "This test case is pass", super.captureScreenshot());
		} else {
			generateExtentTest(testName, false, "There are something wrong in this test", super.captureScreenshot());
		}

	}

	/**
	 * Closes the browser after each test.
	 * 
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	@AfterEach
	public void end() throws InterruptedException {
		quitDriver();
		Thread.sleep(1000);
	}

	/**
	 * Executes final cleanup after all tests have run.
	 */
	@AfterAll
	public static void endAll() {
		endAllTest();
	}

	/**
	 * Tests the selection behavior of buttons within a specific group. Ensures that
	 * only one button can be selected at a time and that others are deselected.
	 * 
	 * @param testName          The name of the test being performed.
	 * @param locatorExpression The XPath expression to locate the button group.
	 * @param groupIndex        The index of the button group being tested.
	 * @throws IOException if an I/O error occurs during test execution.
	 */
	private void testSelection(String testName, String locatorExpression, int groupIndex) throws IOException {
		// Locate the option lines
		WebElement optionElement = driver.findElement(By.xpath(locatorExpression));

		// Locate all the buttons within the option lines
		List<WebElement> options = optionElement.findElements(By.tagName("button"));

		// Boolean to track test success
		boolean error = false;

		int count = options.size();

		// Loop through each button and click it
		for (int i = 0; i < count; i++) {
			try {
				WebElement option = options.get(i);
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
						WebElement otherOption = options.get(j);
						String otherAriaPressed = otherOption.getAttribute("aria-pressed");
						if (otherAriaPressed.equals("true")) {
							throw new Exception("The other buttons are not de-selected");
						}
					}
				}

			} catch (Exception e) {
				byte[] screenshot = captureScreenshot();
				super.generateExtentTest(testName, false, e.getMessage(), screenshot);
				error = true;
			}
		}

		// If there are no errors, pass the test
		if (!error) {
			super.generateExtentTest(testName, true, "This test case is passed");
		}

	}

	/**
	 * Checks the enabled/disabled state of the 'OK' button based on whether all
	 * questions have been answered.
	 * 
	 * @param isAnsweredAllQuestion Indicates if all questions are answered.
	 * @param testName              The name of the test being performed.
	 * @throws IOException if an I/O error occurs during test execution.
	 */
	private void updateOkButtonState(boolean isAnsweredAllQuestion, String testName) throws IOException {
		try {

			WebElement buttonState = driver.findElement(By.xpath("//button[normalize-space(text())='OK']"));

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

			generateExtentTest(testName, false, e.getMessage(), super.captureScreenshot());

		} finally {
			if (isAnsweredAllQuestion) {
				generateExtentTest(testName, true, "This test case is pass");
			}
		}
	}

	/**
	 * Randomly answers a question by clicking one button from the provided options.
	 * 
	 * @param answers1              The list of buttons for the first question.
	 * @param isAnsweredAllQuestion Indicates if all questions are answered.
	 * @param testName              The name of the test being performed.
	 * @throws IOException          if an I/O error occurs during test execution.
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	private void randomAnswerQuestion(List<WebElement> answers1, boolean isAnsweredAllQuestion, String testName)
			throws IOException, InterruptedException {
		// Create a random object for random answer for each question
		Random rnd = new Random();

		// Create Random number
		int x = rnd.nextInt(answers1.size());

		// Click the button
		answers1.get(x).click();

		updateOkButtonState(isAnsweredAllQuestion, testName);

		Thread.sleep(2000);

		// Click the button again for toggle
		answers1.get(x).click();

		Thread.sleep(2000);
	}

	/**
	 * Randomly answers questions from two groups by clicking one button from each.
	 * 
	 * @param answers1              The list of buttons for the first question.
	 * @param answers2              The list of buttons for the second question.
	 * @param isAnsweredAllQuestion Indicates if all questions are answered.
	 * @param testName              The name of the test being performed.
	 * @throws IOException          if an I/O error occurs during test execution.
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	private void randomAnswerQuestion(List<WebElement> answers1, List<WebElement> answers2,
			boolean isAnsweredAllQuestion, String testName) throws IOException, InterruptedException {
		// Create a random object for random answer for each question
		Random rnd = new Random();

		// Create Random number
		int x = rnd.nextInt(answers1.size());
		int y = rnd.nextInt(answers2.size());

		// Click the button
		answers1.get(x).click();
		answers2.get(y).click();

		updateOkButtonState(isAnsweredAllQuestion, testName);

		Thread.sleep(2000);

		// Click the button again for toggle
		answers1.get(x).click();
		answers2.get(y).click();

		Thread.sleep(2000);
	}

	/**
	 * Randomly answers questions from three groups by clicking one button from
	 * each.
	 * 
	 * @param answers1              The list of buttons for the first question.
	 * @param answers2              The list of buttons for the second question.
	 * @param answers3              The list of buttons for the third question.
	 * @param isAnsweredAllQuestion Indicates if all questions are answered.
	 * @param testName              The name of the test being performed.
	 * @throws IOException          if an I/O error occurs during test execution.
	 * @throws InterruptedException if the thread is interrupted during sleep.
	 */
	private void randomAnswerQuestion(List<WebElement> answers1, List<WebElement> answers2, List<WebElement> answers3,
			boolean isAnsweredAllQuestion, String testName) throws IOException, InterruptedException {
		// Create a random object for random answer for each question
		Random rnd = new Random();

		// Create Random number
		int x = rnd.nextInt(answers1.size());
		int y = rnd.nextInt(answers2.size());
		int z = rnd.nextInt(answers3.size());

		// Click the button
		answers1.get(x).click();
		answers2.get(y).click();
		answers3.get(z).click();

		updateOkButtonState(isAnsweredAllQuestion, testName);

		Thread.sleep(2000);

		// Click the button again for toggle
		answers1.get(x).click();
		answers2.get(y).click();
		answers3.get(z).click();

		Thread.sleep(2000);
	}

}
