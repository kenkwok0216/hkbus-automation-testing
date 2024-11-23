package academy.teenfuture.crse.qa.hkbus.selenium.settingpage.strategy;

import org.openqa.selenium.WebDriver;

/**
 * This class is responsible for managing the execution of a specific pop-up
 * strategy. It delegates the handling of the pop-up dialog to the provided
 * strategy implementation.
 * 
 * @author Ken Kwok
 * @see PopUpStrategy
 */
public class PopUpHandler {
	private PopUpStrategy strategy;

	/**
	 * Constructs a PopUpHandler with the specified pop-up strategy.
	 * 
	 * @param strategy the PopUpStrategy implementation to be used for handling
	 *                 pop-ups
	 */
	public PopUpHandler(PopUpStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Executes the pop-up handling strategy using the provided WebDriver.
	 * 
	 * @param driver the WebDriver instance used to interact with the browser
	 * @return true if the pop-up was handled successfully, false otherwise
	 */
	public boolean executeStrategy(WebDriver driver) {
		return strategy.handlePopUp(driver);
	}
}