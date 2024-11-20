package academy.teenfuture.crse.qa.hkbus.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickTab(int index) {
        String xpath = "(//button[@role='tab'])[" + index + "]";
        driver.findElement(By.xpath(xpath)).click();
    }

}
