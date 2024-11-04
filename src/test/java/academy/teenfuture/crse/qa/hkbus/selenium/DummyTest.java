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
        super.configureBrowser("Firefox").get("https://www.google.com.hk");
    }

    @Test
    public void simpleDummyTest() {

    }

    @AfterEach
    public void end() {
        driver.close();
    }

}
