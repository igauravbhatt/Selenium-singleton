package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class sanity {
    @Test(groups = "sanity")
public void sanity() {
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.google.com");
    try { Thread.sleep(5000); } catch (Exception e) {}
   // driver.quit();
}

}