package academy.teenfuture.crse.qa.hkbus.selenium;

import org.json.JSONException;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DummyTest extends BaseTest {

    @Test
    public void simpleDummyTest() {
        driver = super.configureBrowser("Chrome");
        //driver = new ChromeDriver();
        driver.get("https://www.google.com.hk");
        driver.close();
    }

}
