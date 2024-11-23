package academy.teenfuture.crse.qa.hkbus.selenium.customizePopup;

import org.junit.jupiter.api.BeforeEach;

//Bus sorting Order

public class EditBusOrderBySafariTest extends EditBusOrderTest {

	@Override
	@BeforeEach
	public void start() throws InterruptedException {
		super.configureBrowser("Safari").get("https://hkbus.app/en/board");
		Thread.sleep(2000);
	}

}
