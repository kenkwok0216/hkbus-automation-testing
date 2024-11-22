package academy.teenfuture.crse.qa.hkbus.playwright.customizePopup;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import academy.teenfuture.crse.qa.hkbus.playwright.BaseTest;

public class EditBookmarkTest extends BaseTest {
    
	@BeforeEach
	public void start() throws InterruptedException, IOException {
		super.configure("Chrome").navigate("https://hkbus.app/en/");
        Thread.sleep(3000);

	};
    @Test
    public void editBookmarkTest() throws InterruptedException{
        //nev to search page
        Thread.sleep(3000);
        page.locator("//html/body/div[2]/div/div[3]/a[3]").click();
        Thread.sleep(3000);

        //TYPE "3M"
        page.locator("//button[normalize-space(text())='3']").click();;
        Thread.sleep(3000);
        page.locator("//button[normalize-space(text())='M']").click();;
        Thread.sleep(3000);

        //press busStop
        page.locator("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div[2]/div/div/div/a[1]/div/button/div").click();
        Thread.sleep(3000);

        //press star icon
        page.locator("//*[@id=\"stop-0\"]/div[2]/div/div/div/div/div[2]/div[2]/button[2]").click();
        Thread.sleep(3000);

        //press save icon
        page.locator("//html/body/div[3]/div[3]/div/div[2]/div/div[2]").click();
        Thread.sleep(3000);

        page.press("body", "Escape");
        Thread.sleep(3000);



    }


    @AfterEach
	public void endEach() {
		endEachTest();
	}

	@AfterAll
	public static void endAll() {
		endAllTest();
	}
    
}
