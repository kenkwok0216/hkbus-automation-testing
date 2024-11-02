package academy.teenfuture.crse.qa.hkbus.selenium.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class to parse the config.json file
 * 
 * Last modified: 2024-11-3 12:27 AM
 *
 * @author Franck Cheng
 */
public class JSONConfigParser {

    private static final String CONFIG_FILE_PATH = "config.json";

    private static String getConfigPath(String configTypeName, String osName, String browserName) throws JSONException {
        try {
            JSONParser parser = new JSONParser();
            JSONObject config = (JSONObject) parser.parse(new FileReader(CONFIG_FILE_PATH));

            JSONObject driverConfig = (JSONObject) config.get(configTypeName);
            JSONObject osConfig =  (JSONObject) driverConfig.get(osName);
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

    public static String getDriverPath(String osName, String browserName) throws JSONException {
        return getConfigPath("driver", osName, browserName);
    }

    public static String getBrowserPath(String osName, String browserName) throws JSONException {
        return getConfigPath("browser", osName, browserName);
    }
}
