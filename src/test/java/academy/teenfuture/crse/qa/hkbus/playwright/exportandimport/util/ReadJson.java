package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The {@code ReadJson} class provides functionality to read and retrieve values
 * from JSON files. It supports handling nested keys within the JSON structure
 * and can differentiate between retrieving default and saved configurations.
 */
public class ReadJson {
	/**
	 * The directory path where JSON files are stored.
	 */
	static String filePath = System.getProperty("user.dir")
			+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/";

	/**
	 * Reads a value from a specified JSON file based on the provided key.
	 *
	 * @param key       The dot-separated path to the desired value in the JSON
	 *                  file. For example, "Settings.Google Analytics" specifies a
	 *                  nested key.
	 * @param isDefault A boolean indicating whether to read from the default JSON
	 *                  file or the saved JSON file. If true, reads from
	 *                  "default.json"; if false, reads from "saved.json".
	 * @return The value associated with the specified key, which can be either a
	 *         {@link JSONArray} or a {@link String}.
	 * @throws Exception If the specified key does not exist in the JSON structure,
	 *                   or if an error occurs during file reading or parsing.
	 */
	public static Object readJsonFile(String key, boolean isDefault) throws Exception {
		// Build the file path based on whether the default or saved JSON file is
		// requested
		String filePath = System.getProperty("user.dir")
				+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/";
		// Append the appropriate filename to the file path
		filePath = isDefault ? filePath.concat("default.json") : filePath.concat("saved.json");

		// Read the content of the specified JSON file into a String
		String content = new String(Files.readAllBytes(Paths.get(filePath)));

		// Parse the content string into a JSONObject for manipulation
		JSONObject jsonObject = new JSONObject(content);

		// Split the key by dot (.) to handle nested keys (e.g., "Settings.Google
		// Analytics")
		String[] keys = key.split("\\.");
		JSONObject currentObject = jsonObject; // Start with the root JSONObject

		// Iterate over each key in the split key array
		for (int i = 0; i < keys.length; i++) {
			String currentKey = keys[i]; // Get the current key to process

			// Check if we're at the last key in the hierarchy
			if (i == keys.length - 1) {
				// If it's the last key, we attempt to retrieve the value
				if (currentObject.has(currentKey)) {
					// Check if the value is a JSONArray or a String
					if (currentObject.get(currentKey) instanceof JSONArray) {
						// Return the JSONArray if the value is one
						return currentObject.getJSONArray(currentKey);
					} else {
						// Otherwise, return the String value
						return currentObject.getString(currentKey);
					}
				} else {
					// If the key does not exist, throw an exception with a descriptive message
					throw new JSONException("No value found for key: " + key);
				}
			} else {
				// If it's not the last key, we check if the current key exists in the current
				// JSONObject
				if (currentObject.has(currentKey)) {
					// Move deeper into the JSON structure by updating currentObject to the next
					// nested JSONObject
					currentObject = currentObject.getJSONObject(currentKey);
				} else {
					// If the key does not exist, throw an exception indicating the missing object
					throw new JSONException("No object found for key: " + currentKey);
				}
			}
		}
		return null; // Fallback return (this should never be reached)
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