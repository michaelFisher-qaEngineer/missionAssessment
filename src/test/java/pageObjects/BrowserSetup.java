package pageObjects;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.text.MessageFormat;

public class BrowserSetup  {

	String browser = LoadProp.getProperty("Browser");



    /**
     * Browser property location /src/test/java/TestData/TestData.properties
     */


    /**
     * Function for multi browser
     */
    public WebDriver selectBrowser() {
        browser = LoadProp.getProperty("Browser");

        if (browser.equalsIgnoreCase("Chrome")) {
            //System.setProperty("webdriver.chrome.driver", CHROME_WIN);
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            //System.setProperty("webdriver.edge.driver", EDGE);
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            //System.setProperty("webdriver.gecko.driver", FIREFOX_WIN);
            return new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chromeMac")) {
            //System.setProperty("webdriver.chrome.driver", CHROME_MAC);
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        } else if (browser.equalsIgnoreCase("chromeHeadless")) {
            //System.setProperty("webdriver.chrome.driver", CHROME_MAC);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(chromeOptions);
        } else if (browser.equalsIgnoreCase("api")) {

        } else {
            Assert.fail(MessageFormat.format("Wrong Browser: {0}", browser));
        }
		return null;
    }
}
