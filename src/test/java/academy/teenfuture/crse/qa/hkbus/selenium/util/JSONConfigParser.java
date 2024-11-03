package academy.teenfuture.crse.qa.hkbus.selenium.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class to parse the config.json file
 * 
 * Last modified: 2024-11-4 12:21 AM
 *
 * @author Franck Cheng
 */
public class JSONConfigParser {

    // Relative path for config.json file
    private static final String CONFIG_FILE_PATH = "/src/test/java/academy/teenfuture/crse/qa/hkbus/selenium/config.json";

    private static String getConfig(String configTypeName, String platformName, String browserName) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject config = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + CONFIG_FILE_PATH));

            JSONObject driverConfig = (JSONObject) config.get(configTypeName);
            JSONObject osConfig =  (JSONObject) driverConfig.get(platformName);
            if (osConfig != null) {
                String driverPath = (String) osConfig.get(browserName);
                if (driverPath != null) {
                    return driverPath;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     * @param platformName "windows_x64", "macos_aarch64"
     * @param browserName Browser name in lower case (e.g. firefox)
     * @return Driver relative path
     */
    public static String getDriverPath(String platformName, String browserName) {
        return getConfig("driver", platformName, browserName);
    }

    /**
     * 
     * @param platformName "windows_x64", "macos_aarch64"
     * @param browserName Browser name in lower case (e.g. firefox)
     * @return Browser absolute path
     */
    public static String getBrowserPath(String platformName, String browserName) {
        return getConfig("browser", platformName, browserName);
    }
}
