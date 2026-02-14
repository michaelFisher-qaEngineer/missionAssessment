package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
	protected static WebDriver driver;

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public BasePage() {
        // for subclasses that call super() after driver is set
        if (BasePage.driver == null) {
            throw new IllegalStateException("BasePage driver is null. Did you initialize it in Hooks?");
        }
        PageFactory.initElements(BasePage.driver, this);
    }

    public static void setDriver(WebDriver driver) {
        BasePage.driver = driver;
    }

    public static WebDriver getDriver() {
        return BasePage.driver;
    }

}
