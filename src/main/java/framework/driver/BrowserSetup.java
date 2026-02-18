package framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import framework.config.LoadProp;

import java.text.MessageFormat;

public class BrowserSetup  {

//	String browser = LoadProp.getProperty("Browser");
//
//
//
//    /**
//     * Browser property location /src/test/java/TestData/TestData.properties
//     */
//
//
//    /**
//     * Function for multi browser
//     */
//    public WebDriver selectBrowser() {
//        browser = LoadProp.getProperty("Browser");
//
//        if (browser.equalsIgnoreCase("Chrome")) {
//            WebDriverManager.chromedriver().setup();
//            return new ChromeDriver();
//        } else if (browser.equalsIgnoreCase("edge")) {
//            WebDriverManager.edgedriver().setup();
//            return new EdgeDriver();
//        } else if (browser.equalsIgnoreCase("Firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            return new FirefoxDriver();
//        } else if (browser.equalsIgnoreCase("chromeMac")) {
//            WebDriverManager.chromedriver().setup();
//            return new ChromeDriver();
//        } else if (browser.equalsIgnoreCase("chromeHeadless")) {
//            ChromeOptions chromeOptions = new ChromeOptions();
//            chromeOptions.addArguments("--headless");
//            WebDriverManager.chromedriver().setup();
//            return new ChromeDriver(chromeOptions);
//        } else if (browser.equalsIgnoreCase("api")) {
//
//        } else {
//            throw new IllegalStateException(MessageFormat.format("Unsupported browser: {0}. Supported browsers are: Chrome, Edge, Firefox, chromeMac, chromeHeadless, api.", browser));
//        }
//		return null;
//    }
}
