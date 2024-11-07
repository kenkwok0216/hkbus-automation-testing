package academy.teenfuture.crse.qa.hkbus.playwright.settingpage;

import com.microsoft.playwright.Page;

/**
 * Abstract base class for pop-up handling strategies. Each specific pop-up
 * strategy must implement the handlePopUp method.
 * 
 * @author Ken Kwok
 */
public abstract class PopUpStrategy {
	/**
	 * Handles the pop-up interaction.
	 *
	 * @param page     The Playwright page where the pop-up exists.
	 * @param testName The name of the test for reporting purposes.
	 * @return true if the pop-up handling was successful, false otherwise.
	 */
	abstract boolean handlePopUp(Page page);
}
