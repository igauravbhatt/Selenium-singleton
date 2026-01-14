package test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutomationBasicsTest {
    public class AutomationBasicTest {

    WebDriver driver;
    Duration time = Duration.ofSeconds(5);
    String Year = "2020";
    String Month = "04";
    String Date = "17";
    String daysResult;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(time);
        driver.get("https://testautomationpractice.blogspot.com/");

        driver.manage().window().maximize();
        System.out.println("Title of the website: = " + driver.getTitle());
        
    }

    @Test(enabled = false)
    public void Textbox() {
        WebElement nametxtbox = driver.findElement(By.id("name"));
        nametxtbox.clear();
        nametxtbox.sendKeys("Demo");
        driver.findElement(By.xpath("//input[@placeholder='Enter EMail']")).sendKeys("test@t.com");
        driver.findElement(By.xpath("//div[@class=\"form-group\"]//following-sibling::input[3]"))
                .sendKeys("1235465875");
        driver.findElement(By.xpath("//label[contains(.,\"Address\")]//following::textarea"))
                .sendKeys("302 black buck society pin : 4800577");

    }
}
}
