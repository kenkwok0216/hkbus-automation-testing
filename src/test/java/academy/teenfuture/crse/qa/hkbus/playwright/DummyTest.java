package academy.teenfuture.crse.qa.hkbus.playwright;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

/**
 * Template test class
 */
@Disabled
public class DummyTest extends BaseTest {

	@BeforeEach
	public void start() {
		// super.configure("Chrome").navigate("https://www.google.com");
		super.configure("firefox").navigate("https://www.google.com");
	}

	@Test
	public void simpleDummyTest1() throws InterruptedException {
		Locator searchItem = page.locator("//textarea[@name='q']"); // Ctrl-Shift-I to investigate www.google.com.hk
		// site
		int noItems = searchItem.count();
		System.out.println("no. of Text-Area item " + noItems);
		searchItem.fill("xPATH"); // what text "xPATH" to search
		generateExtentTest("Validated Input", true, "Sucessful Input");
		Thread.sleep(3000);
	}

	@Test
	public void simpleDummyTest2() throws InterruptedException, IOException {
		Locator searchItem = page.locator("//textarea[@name='q']"); // Ctrl-Shift-I to investigate www.google.com.hk
		// site
		int noItems = searchItem.count();
		System.out.println("no. of Text-Area item " + noItems);
		searchItem.fill("xPATH"); // what text "xPATH" to search
		generateExtentTest("Validated Input", true, "Sucessful Input", page.screenshot());
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
