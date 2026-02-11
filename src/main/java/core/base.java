package core;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class base {
   static ThreadLocal<WebDriver> driver = new ThreadLocal<>();        
    

    public static void init() {
   
         ChromeOptions options = new ChromeOptions();

        // server-safe options
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--window-size=1366,768");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // options.addArguments("--headless=new"); // 🔥 VERY IMPORTANT
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        driver.set(new ChromeDriver(options));
        
        //driver.get("https://www.google.com");

       
    }

    public static WebDriver getdriver(){
        return driver.get();
    }

   
}