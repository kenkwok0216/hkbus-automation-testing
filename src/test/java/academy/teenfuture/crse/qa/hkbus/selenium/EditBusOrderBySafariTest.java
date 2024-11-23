package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.BeforeEach;

//1. check default > choose another one // get word

public class EditBusOrderBySafariTest extends EditBusOrderTest {

	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Safari").get("https://hkbus.app/zh/board");
	}

}