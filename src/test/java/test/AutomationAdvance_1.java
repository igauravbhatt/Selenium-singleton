package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.ResourceBundle.Control;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.Action;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class AutomationAdvance_1 {
    WebDriver driver;
    String oldUrl;
    SoftAssert soft = new SoftAssert();

    @BeforeClass
    @Parameters({ "browser", "url" })
    public void setup(String browser, String urlp) {

        switch (browser) {
            case "chrome":
            System.setProperty("webdriver.chrome.logfile", "C:\\temp\\chromedriver.log");

// Enable verbose logging to see every internal command
System.setProperty("webdriver.chrome.verboseLogging", "true");
                ChromeOptions options = new ChromeOptions();

                // 🔴 THIS LINE IS NON-NEGOTIABLE
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);

                // server-safe options
                options.addArguments("--window-size=1366,768");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");

               driver = new ChromeDriver(options);

                // fail fast instead of hanging
                driver.manage().timeouts()
                        .pageLoadTimeout(Duration.ofSeconds(60));

                break;
            case "edge":
                EdgeOptions eoptions = new EdgeOptions();
                eoptions.addArguments("--window-size=1920,1080");
                driver = new EdgeDriver(eoptions);

                break;
            case "firefox":
                FirefoxOptions ffoptions = new FirefoxOptions();
                ffoptions.addArguments("--window-size=1920,1080");
                driver = new FirefoxDriver(ffoptions);
                break;

            default:
                System.out.println("No browser value found. Running default on chrome");
                driver = new ChromeDriver();
                break;
        }

        driver.get(urlp);
        System.out.println(browser + " launched with " + urlp);
        driver.manage().window().maximize();
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='50%'");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    @Test(enabled = false)
    public void uploadFiles() {
        driver.findElement(By.id("singleFileInput")).sendKeys("C:\\Users\\dell\\Desktop\\Navya\\rb.png");
        driver.findElement(By.id("multipleFilesInput"))
                .sendKeys("C:\\Users\\dell\\Deskto\\Navya\\rb.png\\n" + "C:\\Users\\dell\\Desktop\\Navya\\hero.png");
        driver.findElement(By.xpath("//button[@type='submit' and contains(.,'Single')]")).click();
        driver.findElement(By.xpath("//button[@type='submit' and contains(.,'Multiple')]")).click();
        By locator = By.id("singleFileStatus");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement singleFileStatus = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        System.out.println(singleFileStatus.getText());
        Assert.assertTrue(singleFileStatus.getText().contains("file selected:"));
    }

    @Test(enabled = false)
    public void staticTablesForLoop() {
        // by for loop
        int rows = driver.findElements(By.xpath("//table[@name='BookTable']//tr")).size();
        int cols = driver.findElements(By.xpath("//table[@name='BookTable']/tbody/tr[1]/th")).size();
        for (int i = 2; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                System.out.print(
                        driver.findElement(By.xpath("//table[@name='BookTable']/tbody/tr[" + i + "]/td[" + j + "]"))
                                .getText());
                System.out.print(" | ");
            }
            System.out.println("");
        }
    }

    @Test(enabled = false)
    public void staticTableList() {
        // by lists
        List<WebElement> rows = driver.findElements(By.xpath("//table[@name='BookTable']//tr"));
        List<WebElement> col = driver.findElements(By.xpath("//table[@name='BookTable']/tbody/tr[1]/th"));

        for (int i = 0; i < rows.size() - 1; i++) {
            List<WebElement> cols = rows.get(i).findElements(By.xpath("td"));
            for (WebElement coll : cols) {
                System.out.print(coll.getText() + " | ");
            }
            System.out.println("");
        }

    };

    @Test(enabled = false)
    public void dynamicTables() {
        List<WebElement> header = driver.findElements(By.xpath("//tr[@id='headers']/th"));
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='taskTable']//tr"));
        int colsSize = header.size();
        System.out.println("size of the  table is " + colsSize + "x" + rows.size());

        for (WebElement h : header) {
            System.out.print(h.getText() + " ");
        }
        System.out.print("\n-----------------------------------------");
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
            for (WebElement col : cols) {
                System.out.print(col.getText() + " | ");
            }
            System.out.println("");
        }

    }

    @Test(enabled = false)
    public void stringLines() {
        Map<String, Double> tableData = new HashMap<>();
        List<WebElement> keys = driver.findElements(By.xpath("//div[@id='displayValues']/p"));
        // List<WebElement> values =
        // driver.findElements(By.xpath("//div[@id='displayValues']/p/strong"));

        for (int i = 0; i < keys.size(); i++) {
            String[] line = keys.get(i).getText().split(":");
            String key = line[0].trim();
            Double value = Double.parseDouble(line[1].replaceAll("[^\\d.]", "").trim());
            System.out.println(key + " " + value);
            tableData.put(key, value);
            // System.out.println("key value stored");
            // String s[] = keys.get(i).getText().split(":");
            // String key = s[0].trim();
            // String value = s[1].trim();
            // key.replaceAll("[^0-9.]","");
            // value.replaceAll("%","");
            // Double val = Double.parseDouble(value);
            // tableData.put(key, val);
        } // driver.findElement(By.xpath("//div[@id='displayValues']/p[contains(.,'CPU')]")).getText();
    }

    @Test(enabled = false)
    public void stringLines2MapLinkedlist() {
        String rawData = driver.findElement(By.xpath("//div[@id='displayValues']")).getText();
        HashMap<String, Double> measurements = new LinkedHashMap<>();
        // List<String> splittedData = new ArrayList<>();
        int i = 0;
        int count = 0;
        String key = "ok";
        Double value = null;
        for (String lines : rawData.split("\n")) {
            for (String line : lines.split(":")) {
                // System.out.println(count++ +"="+line);
                // if (line.trim().isEmpty()) continue;
                line = line.trim();
                if (i % 2 == 0) {
                    key = line;
                    measurements.put(key, null);
                } else {
                    value = Double.parseDouble(line.replaceAll("[^0-9.]", ""));
                    measurements.put(key, value);
                }
                i++;
            }
        }
        // measurements.forEach((k, v) -> System.out.println(k + " = " + v));
        // for(Map.Entry<String,Double> entry :
        // measurements.entrySet()){System.out.println(entry.getKey()+","+entry.getValue());}
        // measurements.forEach((ke,val)->System.out.println(ke+':'+val));
        Double cpu = measurements.get("CPU load of Chrome process");
        Assert.assertTrue(measurements.containsKey("CPU load of Chrome process"));
        Assert.assertTrue(cpu > 0 && cpu < 100, cpu + " is performing well");
        Assert.assertFalse(cpu < 0 && cpu > 100, cpu + " is not performing well");

        // int i = 0;
        // String[] splitted = line.split(":");

        // while (i < splitted.length) {
        // // System.out.print(splitted[i]);
        // System.out.print("splitted["+i+"] ="+splitted[i]+". \n");
        // i++;
        // }

    }

    @Test(enabled = false)
    public void collectionOperations() {
        System.out.println("==============HASHMAP Starts=================");
        Map<String, Integer> map = new HashMap<>();

        map.put("CPU", 5);
        map.put("RAM", 88);
        map.put("Proc", 4);
        map.put("Display", 2);
        map.put("Graphic", 2);
        map.put("Touch", 0);

        // map.forEach((kkk,vvv)->System.out.println(kkk+" , "+vvv));
        // System.out.println("Eenttyset() = "+map.entrySet());
        // System.out.println("getkey() = "+map.get("RAM"));
        // System.out.println("keyset() = "+map.keySet());
        // System.out.println("Values() = "+map.values());
        // System.out.println("contains(key and value) = "+map.containsKey("Display")+"
        // key "+map.containsValue(2));
        // System.out.println("size() = "+map.size());
        // System.out.println("isEmpty() = "+map.isEmpty());
        list_sortreverse(map);
        System.out.println("==============HASHMAP ENDs=================");
    }

    public void list_sortreverse(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> order = new ArrayList<>(map.entrySet());
        order.sort(Map.Entry.comparingByValue());
        System.out.println("==============Comparing by Value=================");
        for (Map.Entry<String, Integer> entry : order) {

            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("==============sorted list by Collections=================");
        Collections.reverse(order);
        List<Integer> sorting = new ArrayList();
        sorting.add(5);
        sorting.add(10);
        sorting.add(500);
        sorting.add(15);
        sorting.add(0);

        System.out.println("sort logic");
        int i = sorting.size();
        int k1 = 0;
        System.out.println("before sorting = " + sorting);
        Collections.sort(sorting);
        System.out.println("After sorting = " + sorting);
        Collections.reverse(sorting);
        System.out.println("After reverse = " + sorting);
        System.out.println("min = " + Collections.min(sorting));
        System.out.println("max = " + Collections.max(sorting));
    }

    @Test(enabled = false)
    public void SimpleAlert() {
        // button[@id='alertBtn']

        driver.findElement(By.xpath("//button[@id='alertBtn']")).click();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        w.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Test(enabled = false)
    public void ConfirmationAlert() {

        driver.findElement(By.xpath("//button[@id='confirmBtn']")).click();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        // w.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
        driver.findElement(By.xpath("//button[@id='confirmBtn']")).click();
        System.out.println(alert.getText());
        alert.dismiss();

    }

    @Test(enabled = false)
    public void PromptAlert() throws InterruptedException {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.xpath("//button[@id='promptBtn']")).click();
        w.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        Thread.sleep(2000);
        String value = "tari bhali thay";
        alert.sendKeys(value);

        Thread.sleep(2000);
        // Thread.sleep(2000);
        // System.out.println(alert.getText());
        // alert.accept();
        // driver.findElement(By.xpath("//button[@id='promptBtn']")).click();
        // System.out.println(alert.getText());
        // alert.dismiss();
    }

    @Test(enabled = false)
    public void dragAndDrop() {
        Actions actions = new Actions(driver);

        WebElement drag = driver.findElement(By.id("draggable"));
        WebElement drop = driver.findElement(By.id("droppable"));
        System.out.println(drag.getText() + " and " + drop.getText());
        actions.dragAndDrop(drag, drop).perform();
        System.out.println(drop.getText());
        Assert.assertTrue(drop.getText().toLowerCase().contains("dropped"));
    }

    @Test(enabled = false)
    public void mouseEvents() throws InterruptedException {
        Actions actions = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//div//button[@class='dropbtn']"));
        System.out.println(ele.getText());
        WebElement ele2 = driver.findElement(By.xpath(
                "//button[@class='dropbtn']/following-sibling::div[@class='dropdown-content']/a[contains(.,'Mobiles')]"));
        WebElement ele3 = driver.findElement(By.xpath(
                "//button[@class='dropbtn']/following-sibling::div[@class='dropdown-content']/a[contains(.,'Laptops')]"));
        System.out.println("mouse right clicked");
        actions.moveToElement(ele).moveToElement(ele2).perform();
        System.out.println(ele2.getText());

        Thread.sleep(4000);
        actions.moveToElement(ele).moveToElement(ele3).perform();
        System.out.println(ele3.getText());
    }

    @Test(enabled = false)
    public void fileReader1() throws IOException {

        // USING WORKBOOKFACTORY

        String path = System.getProperty("user.dir") + "\\resources\\";
        String fileName = "testData.xlsx";
        File file = new File(path + fileName);
        System.out.println(path + fileName);
        FileInputStream fis = new FileInputStream(file);
        // XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // HSSFWorkbook workbook2 = new HSSFWorkbook(fis);

        Workbook workbookAuto = WorkbookFactory.create(fis);
        int count = workbookAuto.getNumberOfSheets() - 1;
        for (int i = 0; i <= count; i++) {
            System.out.println(i + "= " + workbookAuto.getSheetName(i));

        }
        workbookAuto.close();

    }

    @AfterClass(enabled = true)
    public void teardown() throws InterruptedException {
        // Thread.sleep(15000);
        System.out.println("Advance test ended");
        driver.quit();
    }

    @Test(enabled = false)
    public void fileReader2() throws IOException {
        System.out.println("\\\\USING FOR LOOP AND POI XSSF");

        // USING FOR LOOP AND POI XSSF
        String filePath = System.getProperty("user.dir") + "\\resources\\testData.xlsx";
        System.out.println(filePath);
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(1).getLastCellNum();
        DataFormatter formatter = new DataFormatter();
        for (int i = 0; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                XSSFCell cell = row.getCell(j);
                String value = formatter.formatCellValue(cell);
                System.out.print(value + " | ");

            }
            System.out.println();

        }

        workbook.close();
    }

    @Test(dataProvider = "getdata", enabled = false)
    public void filereader3(String[] environmentURL) throws IOException {
        System.out.println("\\\\ USING WORKBOOK FACTORY short VERSION and data provider");
        String filepath = System.getProperty("user.dir") + "\\resources\\testData.xlsx";
        File file = new File(filepath);

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        int rowSize = sheet.getLastRowNum();
        int colSize = sheet.getRow(0).getLastCellNum();
        DataFormatter df = new DataFormatter();
        boolean found = false;
        for (int i = 0; i <= rowSize; i++) {
            Row row = sheet.getRow(i);

            Cell cell = row.getCell(0);
            String value = df.formatCellValue(cell);
            if (value.equalsIgnoreCase(environmentURL[0])) {
                value = df.formatCellValue(row.getCell(1));
                System.out.println("url = " + value + " and env= " + environmentURL[0]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Environment " + environmentURL[0] + " not present in Excel");
        }

    }

    @DataProvider(name = "getdata")
    public String[] dataforexcel() {
        String url[] = { "QA", "SIT", "dev " };
        return url;
    }

    @Test(dataProvider = "dp", enabled = false)
    public void enterText(String name) {
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys(name);
        System.out.println(name);
        driver.findElement(By.xpath("//input[@id='name']")).clear();
    }

    @DataProvider(name = "dp")
    public String[] getData() {
        String datatext[] = { "Gaurav", "Bhawana", "Paresh" };
        return datatext;

    }

    @Test(enabled = false)
    public void windowsHandle() throws InterruptedException {
        driver.get("https://vinothqaacademy.com/multiple-windows/");

        String parent = driver.getWindowHandle();

        driver.findElement(By.name("145newbrowsertab234")).click();

        int j = 1;
        Set<String> name = driver.getWindowHandles();
        for (String child : name) {
            if (!child.equals(parent)) {
                driver.switchTo().window(child);
                System.out.println(j + " = " + driver.getTitle());
                Thread.sleep(2000);
                j++;
            }
        }
    }

    @Test(enabled = false)
    public void FormAndHomeLink() throws InterruptedException {
        String name;
        for (int i = 1; i <= 3; i++) {
            name = "this is text " + i;
            driver.findElement(By.xpath("//input[@id='input" + i + "\']")).sendKeys(name);
            Thread.sleep(500);
            driver.findElement(By.xpath("//button[@id='btn" + i + "\']")).click();

        }
        WebElement ele = driver.findElement(By.xpath("//button[@name='start']"));
        String before = driver.getTitle();
        oldUrl = driver.getCurrentUrl();
        driver.findElement(
                By.xpath("//h2[text()='Footer Links']/ancestor::div[@class='foot section']//a[text()='Home']")).click();
        System.out.println("cllicked");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(ele));
        System.out.println("reloaded");
        String after = driver.getTitle();
        Assert.assertEquals(before, after);
        System.out.println("home Link : Pass");
    }

    @Test(dependsOnMethods = "FormAndHomeLink", enabled = false)
    public void hiddenElementAjaxLinks() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(
                By.xpath("//h2[text()='Footer Links']/ancestor::div[@class='foot section']//a[contains(.,'Hidden')]"))
                .click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)));
        String newPageTitle = driver.findElement(By.xpath("//h3[contains(.,'Hidden Ele')]")).getText();
        Assert.assertTrue(newPageTitle.contains("Hidden"), "Expected title t contain Hidden but found " + newPageTitle);
        Assert.assertTrue(newPageTitle.contains("Hidden"));
        driver.findElement(By.xpath("//div[@id='container']/input[@id='input1']")).sendKeys("text 1");
        WebElement textbox2 = driver.findElement(By.xpath("//div[@id='container']/input[@id='input2']"));
        Assert.assertFalse(textbox2.isDisplayed(), "Expected to be hidden");
        driver.findElement(By.id("toggleInput")).click();
        Assert.assertTrue(textbox2.isDisplayed(), "Expected to be visible");
        textbox2.sendKeys("text2");
        driver.findElement(By.xpath("//input[@id='checkbox1']")).click();
        driver.findElement(By.xpath("//button[@id='toggleCheckbox']")).click();
        driver.findElement(By.xpath("//input[@id='checkbox2']")).click();
        driver.findElement(By.xpath("//button[@id='loadContent']")).click();
        WebElement ajax = driver.findElement(By.xpath("//h2[contains(.,'AJAX')]"));

        wait.until(ExpectedConditions.textToBePresentInElement(ajax, "AJAX Content Loaded"));
        System.out.println("Ajax = " + ajax.getText());
        System.out.println("ajax Link : Pass");

    }

    @Test(dependsOnMethods = "hiddenElementAjaxLinks", enabled = false)
    public void downloadLinks() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement download = driver.findElement(By.xpath("//a[text()='Download Files']"));
        download.click();
        WebElement downloadText = driver.findElement(By.xpath("//textarea[@id='inputText']"));
        wait.until(ExpectedConditions.visibilityOf(downloadText));
        downloadText.sendKeys("this is downloadtext");
        driver.findElement(By.id("generateTxt")).click();

        driver.findElement(By.id("txtDownloadLink")).click();
        String parent = driver.getWindowHandle();
        driver.findElement(By.xpath("//button[text()='Download PDF File']")).click();

        Set<String> tabs = driver.getWindowHandles();
        for (String tab : tabs) {
            if (!tab.equals(parent)) {
                driver.switchTo().window(tab);
                System.out.println("Tab switched to " + tab.toString());
                break;

            } else {
                System.out.println("No tabs found");
            }
        }
        driver.switchTo().window(parent);
        System.out.println("Downnload Link : Pass");
    }

    @Test(enabled = false)
    public void searchlinksAndSwitchTabs() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String parent = driver.getWindowHandle();
        driver.findElement(By.id("Wikipedia1_wikipedia-search-input")).sendKeys("kohli");
        driver.findElement(By.xpath("//input[@class='wikipedia-search-button']")).click();
        List<WebElement> searchResult = driver
                .findElements(By.xpath("(//div[@class='wikipedia-search-main-container']//a)[position()>=2]"));
        int size = 0;
        size = searchResult.size();
        String result = driver.findElement(By.xpath("//div[@class='wikipedia-search-results']")).getText();
        if (size >= searchResult.size()) {
            for (WebElement e : searchResult) {
                // wait.until(ExpectedConditions.)
                System.out.println("Link clicked " + e.getText());
                e.click();
            }
        } else if (result.contains("No results")) {
            System.out.println(result);
            System.out.println("No Results found");
        }
        Set<String> allWindows = driver.getWindowHandles();

        for (String win : allWindows) {
            if (!win.equals(parent)) {
                driver.switchTo().window(win);
                System.out.println("Switched to: " + driver.getTitle());
            }
            driver.switchTo().window(parent);
        }

    }

    @Test(priority = 10, enabled = false)
    public void dynamicButton() {
        WebElement button = driver.findElement(By.xpath("//button[@name='start']"));
        System.out.println("Initially= " + button.getCssValue("background-color"));
        button.click();
        System.out.println("clicked= " + button.getCssValue("background-color"));
        button.click();
        System.out.println("After= " + button.getCssValue("background-color"));

    }

    @Test(priority = 11, enabled = false)
    public void popUpWindow() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String parent = driver.getWindowHandle();
        driver.findElement(By.xpath("//button[@id='PopUp']")).click();

        Set<String> allwindows = driver.getWindowHandles();
        for (String win : allwindows) {
            if (!win.equals(parent)) {
                driver.switchTo().window(win);
                System.out.println(win.toString() + " = " + driver.getTitle());
                if (driver.getTitle().contains("Selenium")) {
                    System.out.println("found");
                    WebElement ReadMore = driver.findElement(By.xpath(
                            "//h4[contains(.,'Selenium WebDriver')]//ancestor::div[@class='card-body']/following-sibling::div//a"));
                    driver.manage().window().maximize();
                    wait.until(ExpectedConditions.elementToBeClickable(ReadMore));
                    ReadMore.click();

                }
            }

        }

        driver.switchTo().window(parent);

    }

    @Test(enabled = false)
    public void doubleClick() {
        driver.findElement(By.xpath("//input[@id='field1']")).sendKeys("I am double click");
        driver.findElement(By.xpath("//button[text()='Copy Text']")).click();
        Actions act = new Actions(driver);
        WebElement copyText = driver.findElement(By.xpath("//button[text()='Copy Text']"));
        // act.doubleClick(copyText).perform();
        ;
        // String valueinText1 = driver.findElement(By.xpath("//button[text()='Copy
        // Text']/following-sibling::p")).getText();
        WebElement valueinText2 = driver.findElement(By.xpath("//input[@id='field2']"));
        String value = valueinText2.getAttribute("value");
        if (value == null || value.trim().isEmpty()) {
            Assert.fail("Textbox field2 is empty");
        } else {
            Assert.assertTrue(true, "Value copied");
        }

    }

    @Test(enabled = true)
    public void sliderByActions() throws InterruptedException {
        WebElement left, right;
        left = driver.findElement(By.xpath("//div[@id='slider-range']/span[position()<=1]"));
        right = driver.findElement(By.xpath("//div[@id='slider-range']/span[position()>1]"));
        Actions move = new Actions(driver);
        move.clickAndHold(left).moveByOffset(-5, 0).release().perform();
        Thread.sleep(10000);
        move.clickAndHold(right).moveByOffset(10, 0).release().perform();
    }

    @Test(enabled = true)
    public void sliderByDragAndDrop() {
        WebElement left, right;
        left = driver.findElement(By.xpath("//div[@id='slider-range']/span[position()<=1]"));
        right = driver.findElement(By.xpath("//div[@id='slider-range']/span[position()>1]"));
        Actions act = new Actions(driver);
        act.dragAndDropBy(left, -10, 0).perform();
        act.dragAndDropBy(right, 10, 0).perform();

    }

    @Test(enabled = false)
    public void laptopLinks() {

        // Way 1 = each and every linnk
        // WebElement link = driver.findElement(By.cssSelector("#apple"));
        // System.out.println(By.cssSelector(".Apple"));
        // if(link.getText().contains("Apple")){
        Actions key = new Actions(driver);
        // key.keyDown(Keys.CONTROL).click(link).keyUp(Keys.CONTROL).perform();

        // }

        // Way 2 = Every link dynamically
        String ids[] = { "apple", "lenovo", "dell" };
        for (String id : ids) {
            WebElement link = driver.findElement(By.cssSelector("#" + id));
            System.out.println(link.getText() + "= clicked");
            key.keyDown(Keys.CONTROL).click(link).keyUp(Keys.CONTROL).perform();

        }

    }

    @Test(enabled = true)
    public void dropdown() throws InterruptedException {
        // way1 not loads scans and load next batch no repeatative
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.xpath("//input[@id='comboBox']")).click();
        WebElement dd = driver.findElement(By.cssSelector("#comboBox + #dropdown"));
        w.until(ExpectedConditions.elementToBeClickable(dd));
        String valueToSelect = "Item 315";
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView(true);", dd);
        boolean found = false;
        int lastcheckedindex = 0;
        String value = null;
        while (true) {
            List<WebElement> options = driver.findElements(By.xpath("//div[@id='dropdown']/div"));

            for (int i = lastcheckedindex; i < options.size(); i++) {

                WebElement option = options.get(i);

                value = option.getText();
                if (option.getText().equalsIgnoreCase(valueToSelect)) {
                    option.click();
                    found = true;
                    System.out.println(i + "=" + option.getText() + " 0 if and size is = " + options.size());
                    break;
                }
            }

            if (found)
                break;

            if (options.size() == lastcheckedindex) {
                System.out.println(value + " 1st if size is = " + options.size());
                break;
            }

            lastcheckedindex = options.size();

            js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", dd);
            Thread.sleep(500);

        }

    }

    /*
     * way2 repetative
     * 
     * while(!found){
     * List<WebElement> options =
     * driver.findElements(By.xpath("//div[@id='dropdown']/div"));
     * for (WebElement option : options) {
     * if (option.getText().equalsIgnoreCase(valueToSelect)) {
     * System.out.println("found " + option.getText());
     * option.click();
     * found = true;
     * break;
     * } else {
     * System.out.println("else checked " + option.getText() + " not found");
     * }
     * }
     * if (found)
     * break;
     * if (!found) {
     * System.out.println("in 3rd if ");
     * js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", dd);
     * List<WebElement> options1 =
     * driver.findElements(By.xpath("//div[@id='dropdown']/div"));
     * System.out.println("new = "+options1.iterator().hasNext());
     * }
     * }
     */

    @Test(enabled = true)
    public void brokenLinks() throws IOException, InterruptedException {
        List<WebElement> links = driver.findElements(By.xpath("//div[@id='broken-links']/a"));
        HttpClient client = HttpClient.newHttpClient();
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url.isBlank() & url.isEmpty() && url.equals(null)) {
                System.out.println(url);
                continue;

            } else {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .method("HEAD", HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
                int status = response.statusCode();

                // Assert.assertTrue(status<=400

                soft.assertTrue(status >= 400, "Broken links " + url + " = " + status);

            }

        }
        soft.assertAll();
    }
}
