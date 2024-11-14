package academy.teenfuture.crse.qa.hkbus.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppBar {

    private WebDriver driver;

    private WebElement appIcon;
    private WebElement searchInput;
    private WebElement langSelector;
    private WebElement darkModeSelector;

    public AppBar(WebDriver driver) {
        this.driver = driver;
    }

}
