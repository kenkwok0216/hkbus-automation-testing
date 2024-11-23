package academy.teenfuture.crse.qa.hkbus.playwright.Page;

import com.microsoft.playwright.Page;

public class PageFactory {

	private Page page;

	public PageFactory(Page page) {
		this.page = page;
	}

	public SettingPage getSettingPage() {
		return new SettingPage(page);
	}

	public HomePage getHomePage() {
		return new HomePage(page);
	}

	public StopPage getStopPage() {
		return new StopPage(page);
	}

	public SearchPage getSearchPage() {
		return new SearchPage(page);
	}

	public P2PPage getP2PPage() {
		return new P2PPage(page);
	}

	public NoticePage getNoticePage() {
		return new NoticePage(page);
	}

	public HeartPage getHeartPage() {
		return new HeartPage(page);
	}

}