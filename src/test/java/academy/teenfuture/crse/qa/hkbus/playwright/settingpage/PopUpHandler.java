package academy.teenfuture.crse.qa.hkbus.playwright.settingpage;

import com.microsoft.playwright.Page;

/**
 * Class to handle the execution of pop-up strategies.
 * 
 * @author Ken Kwok
 */
public class PopUpHandler {
	private PopUpStrategy strategy;

	/**
	 * Constructor to initialize the PopUpHandler with a specific strategy.
	 *
	 * @param strategy The strategy to be used for handling pop-ups.
	 */
	public PopUpHandler(PopUpStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Executes the strategy's pop-up handling method.
	 *
	 * @param page     The Playwright page where the pop-up exists.
	 * @param testName The name of the test for reporting purposes.
	 * @return true if the pop-up handling was successful, false otherwise.
	 */
	public boolean executeStrategy(Page page) {
		return strategy.handlePopUp(page);
	}
}