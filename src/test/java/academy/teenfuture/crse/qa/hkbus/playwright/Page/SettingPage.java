package academy.teenfuture.crse.qa.hkbus.playwright.Page;

import com.microsoft.playwright.Page;

public class SettingPage {

	private Page page;

	public SettingPage(Page page) {
		this.page = page;
	}

	public void GoToSetting() throws InterruptedException {
		page.locator("//*[@id=\"root\"]/div/div[1]/div[3]/a").click();
		Thread.sleep(1000);
	}

	public void ClickButton() {
		// To Do
	}

}