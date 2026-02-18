package framework.driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import framework.config.LoadProp;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public static void initDriver() {
    	if (driver.get() != null) {
            // Already initialized for this thread; don't open a 2nd browser.
            return;
        }
    	String browser = LoadProp.getProperty("Browser");
    	if (browser == null || browser.trim().isEmpty()) {
    	    browser = "chrome";
    	}
    	browser = browser.trim().toLowerCase();
        WebDriver webDriver;

        switch (browser) {
            case "firefox":
                webDriver = createFirefoxDriver();
                break;
            case "edge":
                webDriver = createEdgeDriver();
                break;
            case "chrome":
            case "chromeMac":
            default:
                webDriver = createChromeDriver();
                break;
        }

        driver.set(webDriver);  
    }
    
 // ---------- CHROME ----------
    private static WebDriver createChromeDriver() {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        // fresh profile each run
        options.addArguments("--guest");

        return new ChromeDriver(options);
    }


    // ---------- FIREFOX ----------
    private static WebDriver createFirefoxDriver() {

        FirefoxOptions options = new FirefoxOptions();

        // Disable password manager prompts
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("signon.autofillForms", false);

        return new FirefoxDriver(options);
    }


    // ---------- EDGE ----------
    private static WebDriver createEdgeDriver() {

        EdgeOptions options = new EdgeOptions();

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        return new EdgeDriver(options);
    }


    public static WebDriver getDriver() {
        return driver.get();
    }

    // IMPORTANT: actually closes browser
    public static void quitDriver() {
        WebDriver d = driver.get();
        try {
            if (d != null) {
                d.quit();
            }
        } catch (Exception ignored) {
            // swallow anything from already-closed sessions
        } finally {
            driver.remove();
        }
    }
}
