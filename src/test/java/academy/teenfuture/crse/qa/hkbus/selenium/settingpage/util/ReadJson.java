package academy.teenfuture.crse.qa.hkbus.selenium.settingpage.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

/**
 * Utility class for reading values from a JSON file.
 * 
 * This class provides a method to read the content of a specific JSON file and
 * retrieve the value associated with a given key. It is primarily used in the
 * context of testing to obtain expected URLs for navigation.
 * 
 * @author Ken Kwok
 */
public class ReadJson {
	// This is the path of the json file
	static String filePath = System.getProperty("user.dir")
			+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/selenium/settingpage/navigate.json";

	/**
	 * Reads the value associated with the specified key from the JSON file.
	 *
	 * @param key the key whose associated value is to be retrieved.
	 * @return the value associated with the specified key as a String.
	 * @throws Exception if an error occurs while reading the file or if the key
	 *                   does not exist in the JSON object.
	 */
	public static String readJsonFile(String key) throws Exception {
		// Read the content of the JSON file
		String content = new String(Files.readAllBytes(Paths.get(filePath)));

		// Parse the JSON content
		JSONObject jsonObject = new JSONObject(content);

		return jsonObject.getString(key);
	}
}