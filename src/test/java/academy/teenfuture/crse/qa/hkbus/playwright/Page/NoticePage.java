package academy.teenfuture.crse.qa.hkbus.playwright.Page;

import com.microsoft.playwright.Page;

public class NoticePage {

	private Page page;

	public NoticePage(Page page) {
		this.page = page;
	}

	public void goToP2pPage() {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[5]").click();
	}

}