package academy.teenfuture.crse.qa.hkbus.selenium.customizePopup;

import org.junit.jupiter.api.BeforeEach;

//Bus sorting Order

public class EditRouteFilterByFirefoxTest extends EditRouteFilterTest {

	@Override
	@BeforeEach
	public void start() throws InterruptedException {
		super.configureBrowser("Firefox").get("https://hkbus.app/en");
		Thread.sleep(2000);
	}

}
