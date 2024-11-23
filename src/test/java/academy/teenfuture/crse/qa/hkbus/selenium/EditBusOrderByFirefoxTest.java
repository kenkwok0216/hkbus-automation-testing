package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.BeforeEach;

//1. check default > choose another one // get word

public class EditBusOrderByFirefoxTest extends EditBusOrderTest {

	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Firefox").get("https://hkbus.app/zh/board");
	}

}