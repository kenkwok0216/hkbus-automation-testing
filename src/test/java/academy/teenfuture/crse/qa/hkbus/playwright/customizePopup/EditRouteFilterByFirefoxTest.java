package academy.teenfuture.crse.qa.hkbus.playwright.customizePopup;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

public class EditRouteFilterByFirefoxTest extends EditRouteFilterTest {

	@Override
	@BeforeEach
	public void start() throws InterruptedException, IOException {
		super.configure("Firefox").navigate("https://hkbus.app/en");
		Thread.sleep(3000);

	}
}