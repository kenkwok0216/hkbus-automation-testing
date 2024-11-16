package academy.teenfuture.crse.qa.hkbus.selenium.settingpage.strategy;

import org.openqa.selenium.WebDriver;

/**
 * This abstract class defines the strategy for handling pop-up dialogs within
 * the application. Concrete implementations should provide specific behavior
 * for handling different types of pop-ups.
 */
public abstract class PopUpStrategy {
	/**
	 * Handles the pop-up dialog using the provided WebDriver.
	 * 
	 * @param driver the WebDriver instance used to interact with the browser
	 * @return true if the pop-up was handled successfully, false otherwise
	 */
	abstract boolean handlePopUp(WebDriver driver);
}
