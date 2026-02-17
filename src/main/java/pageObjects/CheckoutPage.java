package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

	public CheckoutPage() {
		super();
	}
	
	@FindBy(css = "#first-name")
	private WebElement firstNameField;
	
	@FindBy(css = "#last-name")
	private WebElement lastNameField;
	
	@FindBy(css = "#postal-code")
	private WebElement postalCodeField;
	
	@FindBy(css = "#cancel")
	private WebElement cancelButton;
	
	@FindBy(css = "#continue")
	private WebElement continueButton;
	
	public void enterFirstName(String firstName) {
		firstNameField.clear();
		firstNameField.click();
		firstNameField.sendKeys(firstName);
	}
	
	public void enterLastName(String lastName) {
		lastNameField.clear();
		lastNameField.click();
		lastNameField.sendKeys(lastName);
	}
	
	public void enterPostalCode(String postalCode) {
		postalCodeField.clear();
		postalCodeField.click();
		postalCodeField.sendKeys(postalCode);
	}
	
	public void clickCancel() {
		cancelButton.click();
	}
	
	public void clickContinue() {
		continueButton.click();
	}
}
