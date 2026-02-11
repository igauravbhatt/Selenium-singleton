package test;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class AutomationBasicsTest {

    WebDriver driver;
    Duration time = Duration.ofSeconds(5);
    String Year = "2024";
    String Month = "04";
    String Date = "17";
    String daysResult;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();
      //  System.setProperty("webdriver.chrome.logfile", "C:\\temp\\chromedriver.log");

        // Enable verbose logging to see every internal command
       // System.setProperty("webdriver.chrome.verboseLogging", "true");
        // 🔴 THIS LINE IS NON-NEGOTIABLE
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        // server-safe options
        options.addArguments("--window-size=1366,768");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless=new");
        //options.addArguments("--user-data-dir=C:\\Windows\\Temp\\chrome-" + System.nanoTime());

        driver = new ChromeDriver(options);

        // fail fast instead of hanging
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(time);
        driver.get("https://testautomationpractice.blogspot.com/");
((JavascriptExecutor) driver).executeScript("document.body.style.zoom='50%'");
        driver.manage().window().maximize();
        System.out.println("Title of the website: = " + driver.getTitle());

    }

    @Test(enabled = true)
    public void Textbox(Method method) {
        System.out.println("================ Started = " + method.getName() + " ================");
        WebElement nametxtbox = driver.findElement(By.id("name"));
        nametxtbox.clear();
        nametxtbox.sendKeys("Demo");
        driver.findElement(By.xpath("//input[@placeholder='Enter EMail']")).sendKeys("test@t.com");
        driver.findElement(By.xpath("//div[@class=\"form-group\"]//following-sibling::input[3]"))
                .sendKeys("1235465875");
        driver.findElement(By.xpath("//label[contains(.,\"Address\")]//following::textarea"))
                .sendKeys("302 black buck society pin : 4800577");

    }

    @Test(enabled = true)
    public void radiobutton(Method method) throws Exception {
        System.out.println("================ Started = " + method.getName() + " ================");
        WebElement Male = driver.findElement(By.xpath("//label[contains(.,'Male')]/preceding-sibling::*[1]"));
        WebElement Female = driver.findElement(By.xpath("//label[contains(.,'Female')]/preceding-sibling::*[1]"));

        if (!Female.isSelected()) {
            Female.click();
            Thread.sleep(1000);

        }
        if (Female.isSelected()) {
            Male.click();
        }

    }

    @Test(enabled = true)
    public void checkbox(Method method) {
        System.out.println("================ Started = " + method.getName() + " ================");
        // input[@id='sunday']
        driver.findElement(By.xpath("//input[@id='sunday']")).click();
        driver.findElement(By.xpath("//input[@id='saturday']")).click();
    }

    @Test(enabled = true)
    public void dropdown(Method method) {
        System.out.println("================ Started = " + method.getName() + " ================");
        WebElement dropdown = driver.findElement(By.xpath("//select[@id='country']"));
        Select select = new Select(dropdown);
        System.out.println("executed");
        select.selectByIndex(4);
        System.out.println("selected");
        System.out.println("selected value is = " + select.getFirstSelectedOption().getText());

    }

    @Test(enabled = true)
    public void multipleSelectDropdown(Method method) {
        System.out.println("================ Started = " + method.getName() + " ================");
        WebElement mulSelDropdown = driver.findElement(By.xpath("//select[@id='colors']"));
        Select select = new Select(mulSelDropdown);
        if (select.isMultiple()) {
            select.selectByIndex(2);
            select.selectByIndex(3);
            select.selectByIndex(4);
        } else {
            System.out.println("Not a multiple selection");
            select.selectByIndex(5);
        }
    }

    @Test(enabled = true)
    public void dateSelector(Method method) throws InterruptedException {
        System.out.println("================ Started = " + method.getName() + " ================");
        driver.findElement(By.xpath("//input[@id=\"datepicker\"]")).click();
        String actualYear = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();
        System.out.println("User Expected Year = " + Year + " and Actual Year = " + actualYear);
        while (!actualYear.equals(Year)) {

            driver.findElement(By.xpath("//a[@title='Prev']")).click();
            actualYear = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();

        }

    }

    @Test(enabled = true)
    public void enterDate(Method method) {
        System.out.println("================ Started = " + method.getName() + " ================");
        driver.findElement(By.xpath("//input[@id=\"datepicker\"]")).sendKeys(Month + "/" + Date + "/" + Year + "/");
        ;

    }

    @Test(enabled = true)
    public void enterDateType2(Method method) throws InterruptedException {
        System.out.println("================ Started = " + method.getName() + " ================");
        String month = "3", Year = "2016", Date = "17";
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.xpath("//input[@name='SelectedDate']")).click();
        Select select;
        // WebElement yearDropDown =
        // driver.findElement(By.xpath("//select[@class='ui-datepicker-year']"));

        // year
        WebElement yearDropDown = driver.findElement(By.xpath("//select[@class='ui-datepicker-year']"));
        select = new Select(yearDropDown);
        wait.until(ExpectedConditions.elementToBeClickable(yearDropDown));
        select.selectByVisibleText(Year);

        // month
        WebElement monthDropDown = driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));
        wait.until(ExpectedConditions.elementToBeClickable(monthDropDown));
        select = new Select(monthDropDown);
        select.selectByValue(month);

        driver.findElement(By.xpath("//a[@class='ui-state-default' and text()='" + Date + "']")).click();
        Thread.sleep(5000);
        String dateselected = driver.findElement(By.xpath("//input[@id='txtDate']")).getAttribute("value");
        System.out.println("dob is  =" + dateselected);
    }

    @Test
    public void startDateEndDate(Method method) {
        System.out.println("================ Started = " + method.getName() + " ================");
        // way 1
        driver.findElement(By.xpath("//input[@id='start-date']")).sendKeys("03-09-1990");
        driver.findElement(By.xpath("//input[@id='end-date']")).sendKeys("04-12-2025");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement submitbtn = driver.findElement(By.xpath("//button[@class='submit-btn']"));
        js.executeScript("arguments[0].scrollIntoView(true);", submitbtn);

        
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(submitbtn));
        submitbtn.click();

        daysResult = driver.findElement(By.id("result")).getText();
        String numbers = daysResult.replaceAll("\\D", "");
        System.out.println(numbers);
        numbers = daysResult.substring(daysResult.indexOf("range of ") + 9, daysResult.indexOf("days"));
        System.out.println("=" + numbers);
        // way 2 using js
    }

    @Test(dependsOnMethods = "startDateEndDate")
    public void reverseWords(Method method) {
        System.out.println("================ Started = " + method.getName() + " =======+=========");
        String[] series = daysResult.split(" ");

        for (int i = series.length - 1; i >= 0; i--) {
            // System.out.println(series[i]);
        }
        int count = 0;
        int count2 = daysResult.length() - 1;

        while (count < daysResult.length()) {
            System.out.print(daysResult.charAt(count));
            count++;
        }
        System.out.println("Reverse");
        while (count2 >= 0) {
            System.out.print(daysResult.charAt(count2));
            count2--;
        }
        System.out.print("");
    }

    @AfterClass(enabled = true)
    public void teardown() throws InterruptedException {
          System.out.println("Basic test ended");
       if (driver != null) {
        driver.quit();
        driver = null;
    }}

}
