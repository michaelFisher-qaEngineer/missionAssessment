package tests.hooks;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import framework.config.LoadProp;
import framework.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class Hook {
	private static final Logger log = LogManager.getLogger(Hook.class);

	private static final int WAIT_SEC = 20;

	@Before("@UI") // no WebDriver for API scenarios
	public void initializeTest(Scenario scenario) {
		log.info("Starting UI scenario: {}", scenario.getName());
		LoadProp.validateRequiredKeys("browser", "url", "screenshotLocation");
		DriverManager.initDriver();
		WebDriver driver = DriverManager.getDriver();

		if (driver == null) {
			throw new RuntimeException("Failed to initialize WebDriver. Check configuration and driver setup.");
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(WAIT_SEC));
		driver.manage().timeouts().scriptTimeout(java.time.Duration.ofSeconds(WAIT_SEC));
		driver.manage().window().maximize();
	}

	/**
	 * Executed after each UI tagged scenario
	 */
	@After("@UI")
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        try {
            if (driver != null && scenario.isFailed()) {

                String browser = LoadProp.getProperty("browser");
                if (browser == null) {
                    browser = "unknownBrowser";
                }

                String screenshotDir = LoadProp.getProperty("screenshotLocation");
                if (screenshotDir == null) {
                    screenshotDir = "target/screenshots/";
                }

                new File(screenshotDir).mkdirs();

                String screenShotFilename = scenario.getName().replace(" ", "")
                        + new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "")
                        + "_" + browser + ".jpg";

                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File outFile = new File(screenshotDir + screenShotFilename);

                FileUtils.copyFile(scrFile, outFile);
                log.info("Saved failure screenshot: {}", outFile.getPath());
            }
        } catch (Exception e) {
            log.warn("Error during teardown/screenshot capture.", e);
        } finally {
            log.info("Finished UI scenario: {} (failed={})", scenario.getName(), scenario.isFailed());
            DriverManager.quitDriver();
        }
    }
}
