package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The {@code WriteJson} class provides functionality to update a JSON file. It
 * allows for modification of specific keys within the JSON structure, including
 * support for handling both single values and arrays.
 */
public class WriteJson {
	/**
	 * The file path for the JSON file to be updated. This path is constructed based
	 * on the current working directory.
	 */
	static String filePath = System.getProperty("user.dir")
			+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/saved.json";

	/**
	 * Updates the value of a specified key in the JSON file.
	 *
	 * @param keyPath  The dot-separated path to the key to be updated. For example,
	 *                 "parentKey.childKey" specifies a nested key.
	 * @param newValue The new value to be set for the specified key.
	 * @throws Exception If the specified key path does not exist in the JSON
	 *                   structure, or if an error occurs during file reading or
	 *                   writing.
	 */
	public static void updateJsonFile(String keyPath, String newValue) throws Exception {
		// Read the existing content
		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonObject = new JSONObject(content);

		// Navigate to the specified key path
		String[] keys = keyPath.split("\\.");
		JSONObject current = jsonObject;
		for (int i = 0; i < keys.length - 1; i++) {
			if (current.has(keys[i])) {
				// Move deeper into the JSON structure
				current = current.getJSONObject(keys[i]);
			} else {
				throw new Exception("Key path not found: " + keyPath);
			}
		}

		// Update the final key
		String lastKey = keys[keys.length - 1];

		// Determine whether to treat the key as a single value or an array
		if (!shouldBeArray(lastKey)) {
			// If the key should be a single value, set it directly
			current.put(lastKey, newValue);
		} else {
			// If the key exists and is an array, add the new value to it
			if (current.has(lastKey) && current.get(lastKey) instanceof JSONArray) {
				JSONArray jsonArray = current.getJSONArray(lastKey);
				jsonArray.put(newValue);
			}
			// If the key exists and is a String, convert it to an array
			else if (current.has(lastKey) && current.get(lastKey) instanceof String) {
				String existingValue = current.getString(lastKey);
				JSONArray newArray = new JSONArray();
				newArray.put(existingValue); // Add the existing string as the first element
				newArray.put(newValue); // Add the new value
				current.put(lastKey, newArray); // Replace the string with the new array
			}
			// If the key doesn't exist, create a new JSONArray
			else {
				JSONArray newArray = new JSONArray();
				newArray.put(newValue);
				current.put(lastKey, newArray);
			}
		}

		// Write the updated content back to the file
		Files.write(Paths.get(filePath), jsonObject.toString(4).getBytes());
	}

	/**
	 * Determines if a specified key should be treated as a single value or as an
	 * array.
	 *
	 * @param key The key to check.
	 * @return {@code true} if the key should be treated as an array; {@code false}
	 *         if it should be treated as a single value.
	 */
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
