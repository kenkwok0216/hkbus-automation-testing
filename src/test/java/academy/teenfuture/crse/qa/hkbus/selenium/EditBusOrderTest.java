package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

//1. check default > choose another one // get word

public class EditBusOrderTest extends BaseTest {

    @BeforeEach
    public void start() {
        super.configureBrowser("Chrome").get("https://hkbus.app/zh/board");
    }

    @Disabled
    @Test
    public void editBusOrder() throws InterruptedException, IOException {

        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement settingPage = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[2]/div/div[1]/div[3]/a")));
        settingPage.click();
        Thread.sleep(1000);

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[2]/div/div[2]/div/ul/div[6]")));
        option.click();
        Thread.sleep(1000);

        WebElement busSortBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[3]/div[3]/div/ul/div[3]")));
        busSortBtn.click();
        Thread.sleep(1000);
        // //*[@id=":r8:"]/button/svg
        WebElement closeBtn = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\":r8:\"]/button/svg")));
        closeBtn.click();
        Thread.sleep(1000);
        driver.get("https://hkbus.app/zh/board");
        Thread.sleep(1000);

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
