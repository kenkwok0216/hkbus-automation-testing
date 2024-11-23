package academy.teenfuture.crse.qa.hkbus.playwright;

import org.junit.jupiter.api.BeforeEach;

public class FontSizeAndKeyBoardByWebkitTest extends FontSizeAndKeyBoardTest {

	@Override
	@BeforeEach
	public void start() {
		// super.configure("Chrome").navigate("https://www.google.com");
		super.configure("Webkit", true).navigate("https://hkbus.app/en/");
		// super.configure("firefox",true);
	}

}
