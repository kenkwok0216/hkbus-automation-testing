package academy.teenfuture.crse.qa.hkbus.selenium;

import org.json.JSONException;
import org.junit.Test;

public class DummyTest extends BaseTest {

    @Test
    public void simpleDummyTest() throws JSONException {
        driver = super.configureBrowser("chrome");
        driver.get("https://www.w3schools.com/");
    }

}
