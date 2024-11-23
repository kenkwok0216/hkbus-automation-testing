package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.BeforeEach;

public class FontSizeAndKeyBoardByChromeTest extends FontSizeAndKeyBoardTest {
	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Chrome", true).get("https://hkbus.app/en/");
	}

}
