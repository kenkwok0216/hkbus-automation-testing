package academy.teenfuture.crse.qa.hkbus.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RoutePage {

    private WebDriver driver;

    @FindBy(xpath = "//div[@class='MuiBox-root hkbus-1mgkggb']//button[1]")
    private WebElement routeStarBtn;

    // @FindBy(xpath = "(//div[@class='MuiBox-root hkbus-15bvnbz']//div)")
    @FindBy(xpath = "(//div[@class='MuiBox-root hkbus-15bvnbz']/child::*)")
    private List<WebElement> stopAccordions;

    public RoutePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getStopAccordion(int index) {
        return stopAccordions.get(index);
    }

    public List<WebElement> getStopAccordions() {
        return stopAccordions;
    }

    public void clickBlankArea() {
        Dimension windowSize = driver.manage().window().getSize();
        int width = windowSize.width;
        
        // Click the top right corner
        Actions action = new Actions(driver);
        // action.moveByOffset((width/2)+1, 0).click().perform();
        action.moveByOffset(0, 0).click().perform();
    }

    public void clickRouteStarBtn() {
        routeStarBtn.click();
    }

    public void clickStopAccordion(int index) {
        stopAccordions.get(index).click();
    }

    public void clickStopFavouriteBtn() {
        driver.findElement(By.xpath("//button[@aria-label='favourite']")).click();
    }

    public void clickCollectionBookmarkTogBtn(int collectionIndex) {
        driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input hkbus-1m9pwf3'])[" + collectionIndex + "]")).click();
    }

    public void clickCollectionOverlay() {
        driver.findElement(By.xpath("//div[contains(@class,'MuiBackdrop-root MuiModal-backdrop')]")).click();
    }

    public void clickAddCollectionBtn() {
        driver.findElement(By.xpath("//h6[text()='Collections']/following-sibling::button")).click();
    }
}
