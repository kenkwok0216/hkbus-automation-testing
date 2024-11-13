package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class ReadJson {
	static String filePath = System.getProperty("user.dir")
			+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/";

	public static Object readJsonFile(String key, boolean isDefault) throws Exception {
		filePath = System.getProperty("user.dir")
				+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/";
		if (isDefault) {
			filePath = filePath.concat("default.json");
		} else {
			filePath = filePath.concat("saved.json");
		}

		// Read the content of the JSON file
		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonObject = new JSONObject(content);

		// Determine if the key should be treated as an array or a single value
		if (shouldBeArray(key)) {
			return jsonObject.getJSONArray(key);
		} else {
			return jsonObject.getString(key);
		}

	}

	// Method to determine if a key should be treated as a single value
	private static boolean shouldBeArray(String key) {
		// Define which keys should hold single values
		String[] singleValueKeys = { "Appearance", "Power Saving Mode", "Platform Display Format", "Refresh Interval",
				"Annotate Scheduled Bus", "Vibration", "Eta Format", "Keyboard Layout", "Route Filtering",
				"Bus Sort Order", "Google Analytics", "Export URL" };

		for (String singleValueKey : singleValueKeys) {
			if (singleValueKey.equals(key)) {
				return false; // This key should be treated as a single value
			}
		}
		return true; // This key should be treated as an array
	}
}