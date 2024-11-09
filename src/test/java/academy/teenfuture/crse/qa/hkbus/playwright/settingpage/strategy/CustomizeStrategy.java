package academy.teenfuture.crse.qa.hkbus.playwright.settingpage.strategy;

import com.microsoft.playwright.Page;

/**
 * Strategy for handling the "Customize" pop-up. This class extends
 * {@link PopUpStrategy} and provides a specific implementation for handling the
 * customization dialog.
 * 
 * @see PopUpStrategy
 * @author Ken Kwok
 */
public class CustomizeStrategy extends PopUpStrategy {
	/**
	 * Handles the pop-up interaction for the "Customize" dialog. It waits for the
	 * dialog to appear, checks the header text, and then closes the dialog.
	 *
	 * @param page The Playwright page where the pop-up exists.
	 * @return true if the pop-up handling was successful (header text matches),
	 *         false otherwise.
	 */
	@Override
	public boolean handlePopUp(Page page) {
		page.waitForSelector("div[role='dialog']");
		String headerText = page.innerText("div[role='dialog'] h2").trim();
		String expectedHeaderText = "Customize";

		boolean result = assertHeaderText(headerText, expectedHeaderText);
		page.press("body", "Escape");
		page.waitForSelector("div[role='dialog']");
		return result;
	}

	/**
	 * Asserts the header text against the expected value and logs the result.
	 *
	 * @param headerText         The actual header text retrieved from the dialog.
	 * @param expectedHeaderText The expected header text.
	 * @param testName           The name of the test for logging purposes.
	 * @return true if the header text matches the expected value, false otherwise.
	 */

	private boolean assertHeaderText(String headerText, String expectedHeaderText) {
		if (headerText.equals(expectedHeaderText)) {
			return true; // Success
		} else {
			return false; // Failure
		}
	}
}