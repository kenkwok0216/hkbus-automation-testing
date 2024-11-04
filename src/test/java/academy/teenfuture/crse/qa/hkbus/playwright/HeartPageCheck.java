package academy.teenfuture.crse.qa.hkbus.playwright;

import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

public class HeartPageCheck extends BaseTest {

	@BeforeEach
	public void start() throws InterruptedException {
		// super.configure("Chrome").navigate("https://www.google.com");
		super.configure("firefox").navigate("https://hkbus.app/en");
		// Locate button to Heart Page and click it
		Locator HeartPage = page.locator("//*[@id=\"root\"]/div/div[3]/a[6]");
		HeartPage.click();
		Thread.sleep(1000);
	}

	@Test
	@Disabled
	public void emojiTest() throws InterruptedException, IOException {
		// Test Name of the method
		String testName = "Test for first question";

		// Locator the emojis option lines
		Locator emojiLocator = page.locator("(//div[@role='group'])[1]");

		// Locator all the option in the emojis lines
		Locator emojis = emojiLocator.locator("button");

		// Get the number of button
		int count = emojis.count();

		// This boolean is to test whether the whole test pass
		boolean error = false;

		// Loop through each button and click it
		for (int i = 0; i < count; i++) {
			try {
				Locator emoji = emojis.nth(i);
				emoji.click();
				Thread.sleep(1000);

				// Get whether the button is clicked
				String ariaPressed = emoji.getAttribute("aria-pressed");
				if (ariaPressed.equals("false")) {
					throw new Exception("The button cannot be selected");
				}

				for (int j = 0; j < count; j++) {
					// Check whether other button is de-selected
					if (j != i) {
						Locator otherEmoji = emojis.nth(j);
						String otherAriaPressed = otherEmoji.getAttribute("aria-pressed");
						if (otherAriaPressed.equals("true")) {
							throw new Exception("The other buttons are not de-selected");
						}
					}
				}

			} catch (Exception e) {

				byte[] screenshot = page.locator("(//div[@class='MuiBox-root hkbus-srxn8'])[1]").screenshot();
				super.generateExtentTest(testName, false, e.getMessage(), screenshot);
				error = true;

			}

		}

		// if there are no error pass the test
		if (!error) {
			byte[] screenshot = page.locator("(//div[@class='MuiBox-root hkbus-srxn8'])[1]").screenshot();
			super.generateExtentTest(testName, true, "This test case is pass", screenshot);
		}
	}

	@Test
	@Disabled
	public void NounTest() throws InterruptedException, IOException {
		// Test Name of the method
		String testName = "Test for second question";

		// Locator the noun option lines
		Locator nounLocator = page.locator("(//div[@role='group'])[2]");

		// Locator all the option in the nouns lines
		Locator nouns = nounLocator.locator("button");

		// Get the number of button
		int count = nouns.count();

		// This boolean is to test whether the whole test pass
		boolean error = false;

		// Loop through each button and click it
		for (int i = 0; i < count; i++) {
			try {
				Locator noun = nouns.nth(i);
				noun.click();
				Thread.sleep(1000);

				// Get whether the button is clicked
				String ariaPressed = noun.getAttribute("aria-pressed");
				if (ariaPressed.equals("false")) {
					throw new Exception("The button cannot be selected");
				}

				for (int j = 0; j < count; j++) {
					// Check whether other button is de-selected
					if (j != i) {
						Locator othernoun = nouns.nth(j);
						String otherAriaPressed = othernoun.getAttribute("aria-pressed");
						if (otherAriaPressed.equals("true")) {
							throw new Exception("The other buttons are not de-selected");
						}
					}
				}

			} catch (Exception e) {

				byte[] screenshot = page.locator("(//div[@class='MuiBox-root hkbus-srxn8'])[2]").screenshot();
				super.generateExtentTest(testName, false, e.getMessage(), screenshot);
				error = true;

			}

		}

		// if there are no error pass the test
		if (!error) {
			byte[] screenshot = page.locator("(//div[@class='MuiBox-root hkbus-srxn8'])[2]").screenshot();
			super.generateExtentTest(testName, true, "This test case is pass", screenshot);
		}
	}

	@Test
	@Disabled
	public void adjectiveTest() throws InterruptedException, IOException {
		// Test Name of the method
		String testName = "Test for third question";

		// Locator the adjective option lines
		Locator adjectiveLocator = page.locator("(//div[@role='group'])[3]");

		// Locator all the option in the adjectives lines
		Locator adjectives = adjectiveLocator.locator("button");

		// Get the number of button
		int count = adjectives.count();

		// This boolean is to test whether the whole test pass
		boolean error = false;

		// Loop through each button and click it
		for (int i = 0; i < count; i++) {
			try {
				Locator adjective = adjectives.nth(i);
				adjective.click();
				Thread.sleep(1000);

				// Get whether the button is clicked
				String ariaPressed = adjective.getAttribute("aria-pressed");
				if (ariaPressed.equals("false")) {
					throw new Exception("The button cannot be selected");
				}

				for (int j = 0; j < count; j++) {
					// Check whether other button is de-selected
					if (j != i) {
						Locator otheradjective = adjectives.nth(j);
						String otherAriaPressed = otheradjective.getAttribute("aria-pressed");
						if (otherAriaPressed.equals("true")) {
							throw new Exception("The other buttons are not de-selected");
						}
					}
				}

			} catch (Exception e) {

				byte[] screenshot = page.locator("(//div[@class='MuiBox-root hkbus-srxn8'])[3]").screenshot();
				super.generateExtentTest(testName, false, e.getMessage(), screenshot);
				error = true;

			}

		}

		// if there are no error pass the test
		if (!error) {
			byte[] screenshot = page.locator("(//div[@class='MuiBox-root hkbus-srxn8'])[3]").screenshot();
			super.generateExtentTest(testName, true, "This test case is pass", screenshot);
		}
	}

	@Test
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

	@Test
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

	@AfterEach
	public void endEach() {
		endEachTest();
	}

	@AfterAll
	public static void endAll() {
		endAllTest();
	}

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
