package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.BeforeEach;

public class FontSizeAndKeyBoardByEdgeTest extends FontSizeAndKeyBoardTest {
	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Edge", true).get("https://hkbus.app/en/");
	}

}
