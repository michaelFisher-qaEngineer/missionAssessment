package mission;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pageObjects.BasePage;
import pageObjects.BrowserSetup;
import pageObjects.LoadProp;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class Hook {

    private static final int WAIT_SEC = 20;

    @Before()
    public void initializeTest() {
    	WebDriver driver = new BrowserSetup().selectBrowser();
    	DriverManager.setDriver(driver);

        if (driver == null) {
            // API-only scenario or misconfig — decide what you want here
            return;
        }

        BasePage.setDriver(driver);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(WAIT_SEC));
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(WAIT_SEC));
        driver.manage().timeouts().scriptTimeout(java.time.Duration.ofSeconds(WAIT_SEC));
    }

    /**
     * Executed after each UI tagged scenario
     */
    @After()
    public void tearDown(Scenario scenario) {
    	WebDriver driver = DriverManager.getDriver();

    	if(!scenario.isFailed()) return;
    	if(driver == null) {
    		DriverManager.unload();
    		return;
    	}
    	
        // take screenshot only on failure
        if (scenario.isFailed()) {
            try {
                String screenShotFilename =
                        scenario.getName().replace(" ", "") +
                        new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "") +
                        "_" + LoadProp.getProperty("Browser") + ".jpg";

                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                FileUtils.copyFile(
                        scrFile,
                        new File(LoadProp.getProperty("ScreenshotLocation") + screenShotFilename)
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // always clean up driver
        try {
            driver.quit();
        } catch (NoSuchSessionException ignored) {}

        DriverManager.unload();
    }
}

