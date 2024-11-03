package academy.teenfuture.crse.qa.hkbus.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Template test class
 */
public class DummyTest extends BaseTest {

    @BeforeEach
    public void start() {
        //driver = super.configureBrowser("Chrome");
        configureBrowser("Firefox");
    }

    @Test
    public void simpleDummyTest() {
        driver.get("https://www.google.com.hk");
    }

    @AfterEach
    public void end() {
        driver.close();
    }

}
