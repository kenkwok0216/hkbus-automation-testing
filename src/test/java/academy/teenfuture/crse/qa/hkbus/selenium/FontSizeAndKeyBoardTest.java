package academy.teenfuture.crse.qa.hkbus.selenium;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import java.util.List;



public class FontSizeAndKeyBoardTest extends BaseTest{
    @BeforeEach
	public void start() {
		super.configureBrowser("Firefox", true).get("https://hkbus.app/en/");
	}

	@Test
    @Disabled
    public void fontSizeInitialTest() throws InterruptedException, IOException {

        // Start getting initial value
        String initialValue;
        boolean error = false;

        Thread.sleep(5000); // Consider using WebDriverWait for better practice

        // Navigate to settings page
        WebElement settingsButton = driver.findElement(By.xpath("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]"));
        settingsButton.click();
        Thread.sleep(2000);

        // Navigate to personal settings
        WebElement personalSettings = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]"));
        personalSettings.click();
        Thread.sleep(2000);

        // Locate the class element of the slider and check whether it is 14
        WebElement initialValueLabel = driver.findElement(By.xpath("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]"));
        initialValue = initialValueLabel.getText();

        // Initial value is obtained

        // Start iteration to check whether each time is the same
        for (int i = 0; i < 10; i++) {
            System.out.println("Iteration: " + (i + 1));

            // Navigate to the initial page again
            driver.get("https://hkbus.app/en/");
            Thread.sleep(5000); // Consider using WebDriverWait for better practice

            // Navigate to settings page
            settingsButton = driver.findElement(By.xpath("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]"));
            settingsButton.click();
            Thread.sleep(2000);

            // Navigate to personal settings
            personalSettings = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/ul/div[6]"));
            personalSettings.click();
            Thread.sleep(2000);

            // Locate the class element of the slider and check whether it is 14
            WebElement valueLabel = driver.findElement(By.xpath("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]"));
            String value = valueLabel.getText();

            if (value.equals(initialValue)) {
                System.out.println("Font size value is maintained: " + value);
            } else {
                System.out.println("Font size value has changed: " + value);
                error = true;
                // Replace this with your logging or reporting method
                System.out.println("The intial font size value changed in iteration: " + i);
                break;
            }
        }

        if (!error) {
            System.out.println("Initial Font Size Value unchanged. This Initial Font Size Value test pass.");
        }

    }

    @Test
    @Disabled
    public void fontSizeChangeTest() throws InterruptedException, IOException {
        // Set up the WebDriver (assuming Chrome here; adjust the path accordingly)
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
       // WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Part 1: Change font size to 10 (smallest size)
        // Navigate to settings page
        WebElement settingsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]")));
        settingsButton.click();

        // Navigate to personal settings
        WebElement personalSettings = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[2]/div/ul/div[6]")));
        personalSettings.click();

        // Locate the slider value label
        WebElement valueLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]")));
        valueLabel.click();
        Thread.sleep(5000); // Adjust this as necessary

        // Navigate to value = 10 by pressing ArrowLeft
        // for (int i = 0; i < 8; i++) {  // 8 presses to reach from 18 to 10
        //     valueLabel.sendKeys(Keys.ARROW_LEFT);
        //     Thread.sleep(1000);
        // }
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_LEFT).perform();
        actions.sendKeys(Keys.ARROW_LEFT).perform();
        actions.sendKeys(Keys.ARROW_LEFT).perform();
        actions.sendKeys(Keys.ARROW_LEFT).perform();
        actions.sendKeys(Keys.ARROW_LEFT).perform();


        // Get font size (only applies in the setting page)
        WebElement font = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]")));
        String fontSizeValue = font.getCssValue("font-size");
        System.out.println(fontSizeValue);

        // Create report for font size 10
        if (fontSizeValue.equals("10px")) { // Ensure the value is compared correctly (including units)
            super.generateExtentTest("fontSizeTest 10", true, "This fontSizeTest pass");
        } else {
            super.generateExtentTest("fontSizeTest 10", false, "The font is not 10", super.captureScreenshot());
        }

        // Part 4: Change font size to 26 (largest size)
        // Leave font page (if needed, adjust this logic based on your UI)
        driver.navigate().back(); // Assuming back takes you to settings; adjust as necessary
        Thread.sleep(2000); // Adjust for loading time

        // Navigate to settings page again
        settingsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]")));
        settingsButton.click();

        // Navigate to personal settings again
        personalSettings = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[2]/div/ul/div[6]")));
        personalSettings.click();

        // Locate the slider value label again
        WebElement valueLabel3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(@class,'MuiSlider-root MuiSlider-colorPrimary')])[2]")));
        valueLabel3.click();

        //Navigate to value = 26 by pressing ArrowRight
        Actions actions2 = new Actions(driver);
        actions2.sendKeys(Keys.ARROW_RIGHT).perform();
        actions2.sendKeys(Keys.ARROW_RIGHT).perform();
        actions2.sendKeys(Keys.ARROW_RIGHT).perform();
        actions2.sendKeys(Keys.ARROW_RIGHT).perform();

        // Get font size (only applies in setting page)
        font = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div[3]/div[3]/div/ul/li/div[2]/p[1]")));
        fontSizeValue = font.getCssValue("font-size");
        System.out.println(fontSizeValue);

        // Create report for font size 26
        if (fontSizeValue.equals("26px")) { // Ensure the value is compared correctly (including units)
            super.generateExtentTest("fontSizeTest 26", true, "This fontSizeTest pass");
        } else {
            super.generateExtentTest("fontSizeTest 26", false, "The font is not 26", super.captureScreenshot());
        }
    }

    
    @Test
    @Disabled
    public void keyboardLayoutCheck() throws InterruptedException, IOException {


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Part 1: Check if order = 123456789c0b
        boolean error = false;

        // Navigate to settings page
        WebElement settingsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]")));
        settingsButton.click();
        Thread.sleep(2000);

        // Navigate to personal settings
        WebElement personalSettings = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[2]/div/ul/div[6]")));
        personalSettings.click();
        Thread.sleep(5000);

        // Find the order = 123456789c0b
        WebElement settingKeyboardLayoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@class,'MuiListItem-root MuiListItem-gutters')]/following-sibling::div[1]")));
        String layoutOrder = settingKeyboardLayoutButton.findElement(By.tagName("p")).getText().trim();
        System.out.println("value : " + layoutOrder);

        // Leave settings page
        WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]")));
        backButton.click();
        Thread.sleep(2000);

        // Click and display Search page
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]")));
        searchButton.click();
        Thread.sleep(2000);

        // Check if buttonboard display according to the order
        WebElement buttonboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div[2]/div[2]/div[1]")));
        WebElement[] numberButtons = buttonboard.findElements(By.tagName("button")).toArray(new WebElement[0]);

        for (int i = 0; i < 9; i++) {
            boolean numberMatch = numberButtons[i].getText().trim().equals(Character.toString(layoutOrder.charAt(i)));
            System.out.println(numberMatch);
            if (numberMatch) {
                System.out.println("NumberMatch");
            } else {
                super.generateExtentTest("Layout Order", false, "The layout order is wrong", super.captureScreenshot());
                error = true;
                break;
            }
        }

        // Part 2.1: Change the order
        // Navigate to settings page again
        WebElement settingsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[1]/div[3]/a")));
        settingsLink.click();
        Thread.sleep(2000);

        // Navigate to personal settings again
        personalSettings = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[2]/div/ul/div[6]")));
        personalSettings.click();
        Thread.sleep(5000);

        // Click and change the layout order
        WebElement settingKeyboardLayoutButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@class,'MuiListItem-root MuiListItem-gutters')]/following-sibling::div[1]")));

        settingKeyboardLayoutButton2.click();
        Thread.sleep(5000);

        // Check if value = 789456123c0b
        String layoutOrder2 = settingKeyboardLayoutButton2.findElement(By.tagName("p")).getText().trim();
        System.out.println("value : " + layoutOrder2);

        // Leave settings page
        backButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]")));
        backButton.click();
        Thread.sleep(2000);

        // Click and display Search page again
        searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]")));
        searchButton.click();
        Thread.sleep(2000);

        // Check if buttonboard displays according to the new order
        WebElement buttonboard2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div[2]/div[2]/div[1]")));
        WebElement[] numberButtons2 = buttonboard2.findElements(By.tagName("button")).toArray(new WebElement[0]);

        for (int i = 0; i < 9; i++) {
            boolean numberMatch = numberButtons2[i].getText().trim().equals(Character.toString(layoutOrder2.charAt(i)));
            System.out.println(numberMatch);
            if (numberMatch) {
                System.out.println("NumberMatch");
            } else {
                super.generateExtentTest("Layout Order", false, "The layout order is wrong", super.captureScreenshot());
                error = true;
                break;
            }
        }

        if (!error) {
            super.generateExtentTest("Layout Order", true, "This test case pass");
        }

    }

    @Test
    @Disabled
    public void historyOnOff() throws InterruptedException, IOException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Part 1: Check if history is On
        // Navigate to settings page
        WebElement settingsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]")));
        settingsButton.click();
        Thread.sleep(2000);

        // Navigate to personal settings
        WebElement personalSettings = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[2]/div/ul/div[6]")));
        personalSettings.click();
        Thread.sleep(5000);

        // Scroll down the page
        WebElement history = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div[3]/div[3]/div/ul/div[11]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", history);
        Thread.sleep(5000);

        // Check if the value is On
        String historyValue = history.findElement(By.tagName("p")).getText().trim();
        System.out.println("Search history value: " + historyValue);
        
        // Leave settings page
        WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]")));
        backButton.click();
        Thread.sleep(5000);

        // Click and display Search page
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]")));
        searchButton.click();
        Thread.sleep(2000);

        // Part 2.1: Check if recent button exists
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div[2]/div[1]/div[1]/div/div/div")));
        List<WebElement> buttons = header.findElements(By.tagName("button"));
        boolean recentExists = false; // Flag to check if "Recent" exists

        // Print all button texts in the header
        for (int i = 0; i < buttons.size(); i++) {
            String buttonText = buttons.get(i).getText().trim(); // Get the text of each button
            System.out.println("Button " + (i + 1) + ": " + buttonText); // Print the button text

            // Check if the button text matches "Recent"
            if (buttonText.equalsIgnoreCase("Recent")) {
                recentExists = true; // Set flag to true if found
                break;
            }
        }

        // Print the result based on the flag
        if (recentExists) {
            System.out.println("Recent exists");
            super.generateExtentTest("Recent button enable", true, "This test case pass");
        } else {
            System.out.println("Recent not exists");
            super.generateExtentTest("Recent button enable", false, "Recent should exist in this case", super.captureScreenshot());
        }

        // Navigate to settings page again
        settingsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class,'MuiButtonBase-root MuiIconButton-root')]")));
        settingsButton.click();
        Thread.sleep(2000);

        // Navigate to personal settings again
        personalSettings = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[2]/div/ul/div[6]")));
        personalSettings.click();
        Thread.sleep(5000);

        // Scroll down the page again
        WebElement history2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div[3]/div[3]/div/ul/div[11]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", history2);
        Thread.sleep(2000);

        // Part 2: Click and turn off the history
        WebElement historyToggle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div[3]/div[3]/div/ul/div[11]")));
        historyToggle.click();
        Thread.sleep(2000);

        // Check if the value is Off
        String historyOffValue = historyToggle.findElement(By.tagName("p")).getText().trim();
        System.out.println("Search history value: " + historyOffValue);

        // Part 2.1: Check if recent button exists after turning off history
        backButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class,'MuiButtonBase-root MuiIconButton-root')])[2]")));
        backButton.click();
        Thread.sleep(2000);

        // Click and display Search page again
        searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]")));
        searchButton.click();
        Thread.sleep(2000);

        // Check for recent button existence again
        header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div[2]/div[1]/div[1]/div/div/div")));
        buttons = header.findElements(By.tagName("button"));
        boolean recentExistsAfterToggle = false; // Flag to check if "Recent" exists

        // Print all button texts in the header
        for (int i = 0; i < buttons.size(); i++) {
            String buttonText = buttons.get(i).getText().trim(); // Get the text of each button
            System.out.println("Button " + (i + 1) + ": " + buttonText); // Print the button text

            // Check if the button text matches "Recent"
            if (buttonText.equalsIgnoreCase("Recent")) {
                recentExistsAfterToggle = true; // Set flag to true if found
                break;
            }
        }

        // Print the result based on the flag
        if (recentExistsAfterToggle) {
            System.out.println("Recent exists");
            super.generateExtentTest("Recent button disable", false, "Recent should not exist in this case", super.captureScreenshot());
        } else {
            System.out.println("Recent not exists");
            super.generateExtentTest("Recent button disable", true, "This test case passed");
        }

        // Close the browser
        driver.quit();
    }




	@AfterEach
	public void end() {
		quitDriver();
	}

	@AfterAll
	public static void endAll() {
		endAllTest();
	}
}
