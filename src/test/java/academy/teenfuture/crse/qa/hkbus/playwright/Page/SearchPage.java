package academy.teenfuture.crse.qa.hkbus.playwright.Page;

import com.microsoft.playwright.Page;

public class SearchPage {

	private Page page;

	public SearchPage(Page page) {
		this.page = page;
	}

	public void navTab(String string) {
		page.locator("//button[normalize-space(text())='" + string + "']").click();

	}

	public void goToSearchPage() {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[2]").click();
	}

}