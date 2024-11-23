package academy.teenfuture.crse.qa.hkbus.selenium;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;

import academy.teenfuture.crse.qa.hkbus.selenium.pages.NavBar;

public class AddBusRouteBySafariTest extends AddBusRouteTest {

	@Override
	@BeforeEach
	public void start() {
		super.configureBrowser("Safari", true).get("https://hkbus.app/en");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		navBar = new NavBar(driver);

	}

}