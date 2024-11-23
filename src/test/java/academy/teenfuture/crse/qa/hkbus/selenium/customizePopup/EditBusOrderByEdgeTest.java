package academy.teenfuture.crse.qa.hkbus.selenium.customizePopup;

import org.junit.jupiter.api.BeforeEach;

//Bus sorting Order

public class EditBusOrderByEdgeTest extends EditBusOrderTest {

	@Override
	@BeforeEach
	public void start() throws InterruptedException {
		super.configureBrowser("Edge").get("https://hkbus.app/en/board");
		Thread.sleep(2000);
	}

}
