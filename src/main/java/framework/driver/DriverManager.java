package framework.driver;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import framework.config.LoadProp;
import framework.listeners.SeleniumLogger;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	private static final Logger log = LogManager.getLogger(DriverManager.class);

	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	public static void initDriver() {
		if (driver.get() != null) {
			// Already initialized for this thread; don't open a 2nd browser.
			log.debug("WebDriver already initialized for this thread; skipping init.");
			return;
		}
		String browser = LoadProp.getProperty("browser");

		if (browser == null || browser.trim().isEmpty()) {
			browser = "chrome";
		}
		browser = browser.trim().toLowerCase();
		log.info("Initializing WebDriver. browser={}", browser);
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
	    // Decorate driver to auto-log clicks/typing/navigation
	    WebDriverListener listener = new SeleniumLogger();
	    WebDriver decoratedDriver = new EventFiringDecorator(listener).decorate(webDriver);

	    driver.set(decoratedDriver);
		log.info("WebDriver initialized. browser={}", browser);
	}

	// ---------- CHROME ----------
	private static WebDriver createChromeDriver() {
		log.debug("Creating ChromeDriver (guest profile, password manager disabled).");
		WebDriverManager.chromedriver().setup();
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
		log.debug("Creating FirefoxDriver (guest profile, password manager disabled).");
		WebDriverManager.firefoxdriver().setup();
		FirefoxOptions options = new FirefoxOptions();

		// Disable password manager prompts
		options.addPreference("signon.rememberSignons", false);
		options.addPreference("signon.autofillForms", false);

		return new FirefoxDriver(options);
	}

	// ---------- EDGE ----------
	private static WebDriver createEdgeDriver() {
		log.debug("Creating EdgeDriver (password manager disabled).");
		WebDriverManager.edgedriver().setup();
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
		if (d == null) {
			log.debug("No WebDriver found for this thread; nothing to quit.");
			return;
		}

		try {
			log.info("Quitting WebDriver.");
			d.quit();
		} catch (Exception e) {
			log.warn("Exception while quitting WebDriver (ignored).", e);
		} finally {
			driver.remove();
		}
	}
}
