package academy.teenfuture.crse.qa.hkbus.selenium.customizePopup;

import org.junit.jupiter.api.BeforeEach;

//Bus sorting Order

public class EditBusOrderByFirefoxTest extends EditBusOrderTest {

	@Override
	@BeforeEach
	public void start() throws InterruptedException {
		super.configureBrowser("Firefox").get("https://hkbus.app/en/board");
		Thread.sleep(2000);
	}

}
