package academy.teenfuture.crse.qa.hkbus.selenium.CollectionTab;

import org.junit.jupiter.api.BeforeEach;

//Add route into collections
public class EditBookmarkByChromeTest extends EditBookmarkTest {

	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Chrome").get("https://hkbus.app/en/board");
	};

}