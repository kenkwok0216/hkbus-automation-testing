package academy.teenfuture.crse.qa.hkbus.playwright.customizePopup;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

//Bus sorting Order

public class EditBusOrderByWebkitTest extends EditBusOrderTest {

	@Override
	@BeforeEach
	public void start() throws InterruptedException, IOException {
		super.configure("Webkit").navigate("https://hkbus.app/en/");
		Thread.sleep(3000);

	};

}