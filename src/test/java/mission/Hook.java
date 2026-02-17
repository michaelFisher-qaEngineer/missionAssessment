package mission;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.LoadProp;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class Hook {

	private static final int WAIT_SEC = 20;

	@Before("@UI") // no WebDriver for API scenarios
	public void initializeTest() {
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

		// take screenshot only on failure
		if (driver != null && scenario.isFailed()) {
			try {
				String screenShotFilename = scenario.getName().replace(" ", "")
						+ new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "") + "_"
						+ LoadProp.getProperty("Browser") + ".jpg";

				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

				FileUtils.copyFile(scrFile, new File(LoadProp.getProperty("ScreenshotLocation") + screenShotFilename));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		DriverManager.quitDriver();
	}
}
