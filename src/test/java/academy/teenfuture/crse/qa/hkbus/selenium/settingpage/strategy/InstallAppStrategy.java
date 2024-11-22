package academy.teenfuture.crse.qa.hkbus.selenium.settingpage.strategy;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class implements a strategy for handling the install app pop-up dialog
 * within the application.
 * 
 * @author Ken Kwok
 * @see PopUpStrategy
 */
public class InstallAppStrategy extends PopUpStrategy {
	/**
	 * Handles the install app pop-up dialog by waiting for it to become visible,
	 * asserting the header text, and closing the dialog.
	 * 
	 * @param driver the WebDriver instance used to interact with the browser
	 * @return true if the header text matches "Installation Steps", false otherwise
	 */
	@Override
	public boolean handlePopUp(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the dialog to be visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));

		// Get the header text
		WebElement dialog = driver.findElement(By.xpath("//div[@aria-labelledby=':r1:']//h2[1]"));
		String headerText = dialog.getText().trim();

		boolean result = assertHeaderText(headerText, "Installation Steps");

		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ESCAPE).perform();

		// Wait for the dialog to disappear
		wait.until(ExpectedConditions.invisibilityOf(dialog));

		// Wait for the dialog to disappear
		wait.until(ExpectedConditions.invisibilityOf(dialog));

		return result;
	}

	/**
	 * Asserts that the provided header text matches the expected header text.
	 * 
	 * @param headerText         the header text to check
	 * @param expectedHeaderText the expected header text to match against
	 * @return true if the header text matches the expected text, false otherwise
	 */
	private boolean assertHeaderText(String headerText, String expectedHeaderText) {
		if (headerText.equals(expectedHeaderText)) {
			return true; // Success
		} else {
			return false; // Failure
		}
	}
}