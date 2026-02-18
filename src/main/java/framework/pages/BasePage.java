package framework.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import framework.driver.DriverManager;

public class BasePage {
	protected WebDriver driver;
	protected final Logger log = LogManager.getLogger(getClass());
    public BasePage() {
    	this.driver = DriverManager.getDriver();
    	if (this.driver == null) {
            throw new IllegalStateException(
                "ThreadLocal WebDriver is null. Did you initialize DriverManager.initDriver() in your Hooks @Before?"
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
