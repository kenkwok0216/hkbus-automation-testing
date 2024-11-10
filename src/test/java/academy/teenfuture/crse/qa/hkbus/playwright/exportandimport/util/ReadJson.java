package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class ReadJson {
	static String filePath = System.getProperty("user.dir")
			+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/";

	public static String readJsonFile(String key, boolean isDefault) throws Exception {
		if (isDefault) {
			filePath.concat("default.json");

			// Read the content of the JSON file
			String content = new String(Files.readAllBytes(Paths.get(filePath)));

			// Parse the JSON content
			JSONObject jsonObject = new JSONObject(content);

			return jsonObject.getString(key);

		} else {
			filePath.concat("save.json");
			// Read the content of the JSON file
			String content = new String(Files.readAllBytes(Paths.get(filePath)));

			// Parse the JSON content
			JSONObject jsonObject = new JSONObject(content);

			return jsonObject.getString(key);
		}

	}
}