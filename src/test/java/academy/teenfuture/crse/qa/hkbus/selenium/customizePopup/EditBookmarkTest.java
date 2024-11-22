// package academy.teenfuture.crse.qa.hkbus.selenium.customizePopup;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import java.util.List;

// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

// import academy.teenfuture.crse.qa.hkbus.selenium.BaseTest;

// import java.time.Duration;


// public class EditBookmarkTest extends BaseTest {
// @BeforeEach
//         public void start() {
//                 super.configureBrowser("Chrome").get("https://hkbus.app/en/board");
//         }

//         @Test
//         public void editBookmark() throws InterruptedException {
//         Thread.sleep(3000);
//         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

//         WebElement numberPanel1 = wait
//                 .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='5']")));
//         WebElement numberPanel2 = wait
//                 .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='9']")));
//         WebElement numberPanel3 = wait
//                 .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='X']")));
//         numberPanel1.click();
//         numberPanel2.click();
//         numberPanel3.click();
//         Thread.sleep(1000);

//         List<WebElement> busStop = driver.findElements(
//                 By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div[2]/div/div/div/a[1]/div/button/div"));

//         busStop.get(0).click();
//         Thread.sleep(1000);

//         WebElement addBusStopBtn = wait.until(ExpectedConditions
//                 .elementToBeClickable(By.xpath("//*[@id=\"stop-0\"]/div[2]/div/div/div/div/div[2]/div[2]/button[2]")));
//         addBusStopBtn.click();
//         Thread.sleep(1000);

//         WebElement saveBtn = wait.until(ExpectedConditions
//                 .elementToBeClickable(By.xpath("//html/body/div[3]/div[3]/div/div[2]/div/div[2]")));
//         saveBtn.click();
//         Thread.sleep(1000);

//         Actions actions = new Actions(driver);
//         actions.sendKeys(Keys.ESCAPE).perform();
//         Thread.sleep(1000);

//         WebElement MainPageBtn = wait.until(ExpectedConditions
//                 .elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[3]/a[1]")));
//         MainPageBtn.click();
//         Thread.sleep(1000);

//         WebElement starredCollection = wait.until(ExpectedConditions
//                 .elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div[3]/div/button[2]")));
//         starredCollection.click();
//         Thread.sleep(1000);

//         }

//         @AfterEach
//         public void end() {
//                 quitDriver();
//         }

//         @AfterAll
//         public static void endAll() {
//         endAllTest();
//         }
// }