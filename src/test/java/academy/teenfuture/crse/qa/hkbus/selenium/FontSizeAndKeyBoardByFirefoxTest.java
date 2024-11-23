package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.BeforeEach;

public class FontSizeAndKeyBoardByFirefoxTest extends FontSizeAndKeyBoardTest {
	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Firefox", true).get("https://hkbus.app/en/");
	}

}
