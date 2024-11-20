package academy.teenfuture.crse.qa.hkbus.selenium.pages;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

    private static List<String> routePageUrls;

    private WebDriver driver;

    static {
        // Using previous scraped URLs instead of real time data
        routePageUrls = extractRouteLinks();
    }
    
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Extract previous route url list from local json file
     * @return List of route detail page url
     */
    private static List<String> extractRouteLinks() {
        String filePath = System.getProperty("user.dir") + "/ButtonLinks/links_20241114.json";

        // Parse the JSON file
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Get the "links" array
        JSONArray links = (JSONArray) jsonObject.get("links");

        // Extract URLs and store them in a list
        List<String> urlList = new ArrayList<>();
        for (Object link : links) {
            urlList.add((String) link);
        }

        return urlList;
    }

    public static List<String> getRoutePageUrls() {
        return routePageUrls;
    }

    /**
     * Click a button on the in app keyboard
     * @param key 
     */
    public void clickInAppKeyboardBtn(String key) {
        String xpath = "//button[normalize-space(text())='" + key + "']";
        driver.findElement(By.xpath(xpath)).click();;

    }

    /**
     * Input a string by clicking the in app keyboard
     * @param str String that input to the search field
     * @param interval Interval time of each click action
     * @throws InterruptedException
     */
    public void clickInAppKeyboardBtns(String str, long interval) throws InterruptedException {
        for (char c : str.toCharArray()) {
            clickInAppKeyboardBtn(String.valueOf(c));
            Thread.sleep(interval);
        }
    }

    /**
     * Click the route cell on the list
     * @param index
     * @return
     */
    public RoutePage clickRouteCell(int index) {
        // String xpath = "//a[@href[starts-with(., '/en/route/')]]";
        // List<WebElement> elements = driver.findElements(By.xpath(xpath));
        // elements.get(index).click();

        // Not using click, directly go to the route page
        driver.get(getRoutePageUrls().get(index));

        return new RoutePage(driver);
    }

}
