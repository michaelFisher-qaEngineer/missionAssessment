package framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.config.LoadProp;

public class HomePage extends BasePage {

	public HomePage() {
		super();
	}
	
	//locators
	@FindBy(css = "#user-name")
	WebElement userNameField;
	
	@FindBy(css = "#password")
	WebElement passwordField;
	
	@FindBy(css = "#login-button")
	WebElement loginButton;
	
	public void enterUserName(String userName) {
        log.info("Opening entering username: {}", userName);
		userNameField.sendKeys(userName);
	}
	
	public void enterPassword(String password) {
		passwordField.sendKeys(password);
	}
	
	public void clickLogin() {
		loginButton.click();
	}

	public void open() {
        driver.get(LoadProp.getProperty("url"));
    }
	
}
