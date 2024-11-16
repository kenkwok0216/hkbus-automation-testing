package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Tests extends BaseTest {

    @BeforeEach
    public void start() {
        super.configureBrowser("Chrome").get("https://hkbus.app/en/board");
    }

    @Test
    public void editBookmark() throws InterruptedException {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement numberPanel1 = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='5']")));
            WebElement numberPanel2 = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='9']")));
            WebElement numberPanel3 = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='X']")));
            numberPanel1.click();
            numberPanel2.click();
            numberPanel3.click();
            Thread.sleep(1000);

            List<WebElement> busStop = driver
                    .findElements(By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div[2]/div/div"));

            busStop.get(0).click();

        } catch (NoSuchElementException e) {
            System.out.println("未找到: " + e.getMessage());
        } finally {
            Thread.sleep(5000);
        }
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