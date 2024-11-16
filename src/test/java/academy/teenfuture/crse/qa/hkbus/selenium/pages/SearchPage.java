package academy.teenfuture.crse.qa.hkbus.selenium.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

    private WebDriver driver;
    
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickInAppKeyboardBtn(String key) {
        String xpath = "//button[normalize-space(text())='" + key + "']";
        driver.findElement(By.xpath(xpath)).click();;

    }

    public void clickInAppKeyboardBtns(String str, long interval) throws InterruptedException {
        for (char c : str.toCharArray()) {
            clickInAppKeyboardBtn(String.valueOf(c));
            Thread.sleep(interval);
        }
    }

    public RouteDetailPage clickRouteCell(int index) {
        String xpath = "//a[@href[starts-with(., '/en/route/')]]";
        
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        elements.get(index).click();

        return new RouteDetailPage(driver);
    }

    /*
     * Inner class
     */
    public class RouteCell {

        private String routeName;
        private String origin;
        private String destination;
    }

}
