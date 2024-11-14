package academy.teenfuture.crse.qa.hkbus.selenium.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

    // private String[] busRouteNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    // private String[] busRouteLetters = {"A", "B", "E", "H", "K", "N", "P", "R", "S", "T", "W", "X"};

    // @FindBy(xpath = "//button[normalize-space(text())='0']")
    // private WebElement btn0;

    // @FindBy(xpath = "//button[normalize-space(text())='1']")
    // private WebElement btn1;

    // @FindBy(xpath = "//button[normalize-space(text())='2']")
    // private WebElement btn2;

    // @FindBy(xpath = "//button[normalize-space(text())='3']")
    // private WebElement btn3;

    // @FindBy(xpath = "//button[normalize-space(text())='4']")
    // private WebElement btn4;

    // @FindBy(xpath = "//button[normalize-space(text())='5']")
    // private WebElement btn5;

    // @FindBy(xpath = "//button[normalize-space(text())='6']")
    // private WebElement btn6;

    // @FindBy(xpath = "//button[normalize-space(text())='7']")
    // private WebElement btn7;

    // @FindBy(xpath = "//button[normalize-space(text())='8']")
    // private WebElement btn8;

    // @FindBy(xpath = "//button[normalize-space(text())='9']")
    // private WebElement btn9;

    // @FindBy(xpath = "//button[normalize-space(text())='A']")
    // private WebElement btnA;

    // @FindBy(xpath = "//button[normalize-space(text())='B']")
    // private WebElement btnB;

    // @FindBy(xpath = "//button[normalize-space(text())='E']")
    // private WebElement btnE;

    // @FindBy(xpath = "//button[normalize-space(text())='H']")
    // private WebElement btnH;

    // @FindBy(xpath = "//button[normalize-space(text())='K']")
    // private WebElement btnK;

    // @FindBy(xpath = "//button[normalize-space(text())='N']")
    // private WebElement btnN;

    // @FindBy(xpath = "//button[normalize-space(text())='P']")
    // private WebElement btnP;

    // @FindBy(xpath = "//button[normalize-space(text())='R']")
    // private WebElement btnR;

    // @FindBy(xpath = "//button[normalize-space(text())='S']")
    // private WebElement btnS;

    // @FindBy(xpath = "//button[normalize-space(text())='T']")
    // private WebElement btnT;

    // @FindBy(xpath = "//button[normalize-space(text())='W']")
    // private WebElement btnW;

    // @FindBy(xpath = "//button[normalize-space(text())='X']")
    // private WebElement btnX;

    private WebDriver driver;
    
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickInAppKeyboardBtn(String key) {
        String xpath = "//button[normalize-space(text())='" + key + "']";
        driver.findElement(By.xpath(xpath)).click();;

    }

    public void clickInAppKeyboardBtns(String str, long millis) throws InterruptedException {
        for (char c : str.toCharArray()) {
            clickInAppKeyboardBtn(String.valueOf(c));
            Thread.sleep(millis);
        }
    }

}
