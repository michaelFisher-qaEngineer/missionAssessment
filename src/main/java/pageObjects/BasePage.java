package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import driver.DriverManager;

public class BasePage {
	protected WebDriver driver;

    public BasePage() {
    	this.driver = DriverManager.getDriver();
    	if (this.driver == null) {
            throw new IllegalStateException(
                "ThreadLocal WebDriver is null. Did you initialize DriverManager.setDriver(...) in your Hooks @Before?"
            );
        }
        PageFactory.initElements(this.driver, this);

    }
    
    public BasePage(WebDriver driver) {
        // for subclasses that call super() after driver is set
        if (driver == null) {
            throw new IllegalStateException("BasePage driver is null. Did you initialize it in Hooks?");
        }
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

}
