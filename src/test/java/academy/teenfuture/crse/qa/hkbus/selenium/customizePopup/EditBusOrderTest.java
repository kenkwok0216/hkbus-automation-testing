package academy.teenfuture.crse.qa.hkbus.selenium.customizePopup;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import academy.teenfuture.crse.qa.hkbus.selenium.BaseTest;

import java.io.IOException;
import java.time.Duration;

//Bus sorting Order

public class EditBusOrderTest extends BaseTest {

    @BeforeEach
    public void start() throws InterruptedException {
        super.configureBrowser("Chrome").get("https://hkbus.app/en/board");
        Thread.sleep(2000);
    }

    @Disabled
    @Test
    public void editBusOrder() throws InterruptedException, IOException {

        String initialValue;

        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // nav to setting page
        WebElement settingPage = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[2]/div/div[1]/div[3]/a")));
        settingPage.click();
        Thread.sleep(1000);

        // click option button ,appear popup
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[2]/div/div[2]/div/ul/div[6]")));
        option.click();
        Thread.sleep(1000);       
        


        // click bus sorting
        WebElement busSortBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[3]/div[3]/div/ul/div[3]")));
        busSortBtn.click();
        Thread.sleep(1000);

        // close the popup
        WebElement closeBtn = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r1:\"]/button")));
        closeBtn.click();
        Thread.sleep(1000);

        // nav to home page
        driver.get("https://hkbus.app/en/board");
        Thread.sleep(3000);
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
