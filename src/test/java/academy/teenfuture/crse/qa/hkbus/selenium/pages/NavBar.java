package academy.teenfuture.crse.qa.hkbus.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavBar {

    private WebDriver driver;

    @FindBy(xpath = "(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[1]")
    private WebElement navHome;

    private WebElement navStops;

    @FindBy(xpath = "(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]")
    private WebElement navSearch;

    private WebElement navP2P;

    private WebElement navNotice;

    private WebElement navHeart;

    public NavBar(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public HomePage clickNavHome() {
        // driver.findElement(By.xpath("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[1]")).click();
        navHome.click();
        return new HomePage(driver);
    }

    public void clickNavStops() {
        // TODO
    }

    public SearchPage clickNavSearch() {
        // driver.findElement(By.xpath("(//a[contains(@class,'MuiButtonBase-root MuiBottomNavigationAction-root')])[3]")).click();
        navSearch.click();
        return new SearchPage(driver);
    }

    public void clickNavP2P() {
        // TODO
    }

    public void clickNavNotice() {
        // TODO
    }

    public void clickNavHeart() {
        // TODO
    }
        
}
