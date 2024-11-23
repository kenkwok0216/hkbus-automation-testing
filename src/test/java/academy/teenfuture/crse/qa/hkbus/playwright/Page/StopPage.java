package academy.teenfuture.crse.qa.hkbus.playwright.Page;

import com.microsoft.playwright.Page;

public class StopPage {

	private Page page;

	public StopPage(Page page) {
		this.page = page;
	}

	public void goToStopPage() {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[2]").click();
	}

}