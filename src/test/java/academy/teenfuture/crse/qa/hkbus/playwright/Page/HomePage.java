package academy.teenfuture.crse.qa.hkbus.playwright.Page;

import com.microsoft.playwright.Page;

public class HomePage {

	private Page page;

	public HomePage(Page page) {
		this.page = page;
	}

	public void navTab(String string) {
		page.locator("//button[normalize-space(text())='" + string + "']").click();

	}

	public void goToHomePage() {
		page.locator("//*[@id=\"root\"]/div/div[3]/a[1]").click();
	}

}