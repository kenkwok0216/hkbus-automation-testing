package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.Test;

public class DummyTest extends BaseTest {

    @Test
    public void simpleDummyTest() {
        driver = super.configureBrowser("Chrome");
        //driver = new ChromeDriver();
        driver.get("https://www.google.com.hk");
        driver.close();
    }

}
