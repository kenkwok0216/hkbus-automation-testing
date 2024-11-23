package academy.teenfuture.crse.qa.hkbus.selenium.CollectionTab;

import org.junit.jupiter.api.BeforeEach;

//Add route into collections
public class EditBookmarkBySafariTest extends EditBookmarkTest {

	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Safari").get("https://hkbus.app/en/board");
	};

}