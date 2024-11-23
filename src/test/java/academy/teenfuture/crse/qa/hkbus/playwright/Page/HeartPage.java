package academy.teenfuture.crse.qa.hkbus.playwright.Page;

import com.microsoft.playwright.Page;

public class HeartPage {

	private Page page;

	public HeartPage(Page page) {
		this.page = page;
	}

	public void goToP2pPage() {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[6]").click();
	}

	public void ClickButton() {
		// To Do
	}
}