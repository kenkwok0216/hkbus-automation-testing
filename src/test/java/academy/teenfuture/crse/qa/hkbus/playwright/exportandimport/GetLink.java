package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.microsoft.playwright.Locator;

import academy.teenfuture.crse.qa.hkbus.playwright.BaseTest;

public class GetLink extends BaseTest {

	public static void main(String args[]) throws InterruptedException, JSONException {

		BaseTest basetest = new BaseTest();

		basetest.configure("firefox", false).navigate("https://hkbus.app/en");
		page.locator("//*[@id=\"root\"]/div/div[3]/a[3]").click();
		page.locator("(//button[@role='tab'])[2]").click();

		// Get the all route
		Locator allRoute = page.locator("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div[2]/div/div");

		// Create a JSON array to store the links
		JSONArray linksArray = new JSONArray();
		Set<String> collectedLinks = new HashSet<>();

		// Define the target href to scroll to
		String targetHref = "/en/route/x11r-5-sheraton-hong-kong-tung-chung-hotel-tai-o";
		boolean targetFound = false;

		while (!targetFound) {
			// Get all route links currently in the container
			Locator routeButtonLocator = allRoute.locator("a");
			int count = routeButtonLocator.count();

			// Collect links that are currently visible
			for (int i = 0; i < count; i++) {
				String content = routeButtonLocator.nth(i).getAttribute("href");
				System.out.println(content);

				// Check if content is not null and not already collected
				if (content != null && collectedLinks.add(content)) {
					linksArray.put("https://hkbus.app/" + content); // Add valid link to the JSON array
				}

				if (content.equals(targetHref)) {
					targetFound = true; // Mark target as found
					break;
				}
			}

			// Scroll down the container
			allRoute.evaluate("element => element.scrollTop += 64");

			// Thread.sleep(3000);

		}

		// Create a JSON object to hold the array
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("links", linksArray);

		// Write the JSON object to a file
		String dateString = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filePath = System.getProperty("user.dir") + "/ButtonLinks/links_" + dateString + ".json";

		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(jsonObject.toString(4)); // Write with indentation for readability
			System.out.println("Links successfully written to " + filePath);
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
			e.printStackTrace();
		}

		page.close();
		// browserContext.close();
		playwright.close();

	}

}