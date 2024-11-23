package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.BeforeEach;

public class FontSizeAndKeyBoardBySafariTest extends FontSizeAndKeyBoardTest {
	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Safari", true).get("https://hkbus.app/en/");
	}

}
