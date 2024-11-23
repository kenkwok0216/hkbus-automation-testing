package academy.teenfuture.crse.qa.hkbus.playwright.Page;

import com.microsoft.playwright.Page;

public class P2PPage {

	private Page page;

	public P2PPage(Page page) {
		this.page = page;
	}

	public void goToP2PPage() {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[4]").click();
	}

}