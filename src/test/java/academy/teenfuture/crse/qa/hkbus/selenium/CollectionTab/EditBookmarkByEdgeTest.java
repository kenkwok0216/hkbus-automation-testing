package academy.teenfuture.crse.qa.hkbus.selenium.CollectionTab;

import org.junit.jupiter.api.BeforeEach;

//Add route into collections
public class EditBookmarkByEdgeTest extends EditBookmarkTest {

	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Edge").get("https://hkbus.app/en/board");
	};

}