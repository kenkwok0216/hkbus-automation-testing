package academy.teenfuture.crse.qa.hkbus.playwright.exportandimport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;

import academy.teenfuture.crse.qa.hkbus.playwright.Page.PageFactory;

public class ExportandImportByWebkitTest extends ExportandImportTest {

	@BeforeEach
	@Override
	public void start() throws InterruptedException {
		addedStops = 0;
		for (int i = 0; i < addedcollections.length; i++) {
			addedcollections[i] = 0;
		}

		super.configure("webkit").navigate("https://hkbus.app/en");
		GoPage = new PageFactory(page);
		// This is to handle the saved.json to store the edited item
		// Source file path
		Path sourcePath = Paths.get(System.getProperty("user.dir")
				+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/default.json");
		// Destination file path
		Path destinationPath = Paths.get(System.getProperty("user.dir")
				+ "/src/test/java/academy/teenfuture/crse/qa/hkbus/playwright/exportandimport/saveData/saved.json");

		try {
			// Copy the file
			Files.copy(sourcePath, destinationPath);
			System.out.println("File copied successfully!");
		} catch (IOException e) {
			System.err.println("Error occurred while copying the file: " + e.getMessage());
		}

	}

	// All test methods from HeartPageCheck will automatically be included
}