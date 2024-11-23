package academy.teenfuture.crse.qa.hkbus.playwright.CollectionTab;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

//Add route into collections
public class EditBookmarkByWebkitTest extends EditBookmarkTest {

	@Override
	@BeforeEach
	public void start() throws InterruptedException, IOException {
		super.configure("Webkit").navigate("https://hkbus.app/en/");
		Thread.sleep(3000);

	};

}
