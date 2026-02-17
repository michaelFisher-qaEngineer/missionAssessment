package pageObjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutOverviewPage extends BasePage {

	public CheckoutOverviewPage() {
		super();
	}

	private final By cartItems = By.cssSelector(".cart_item");
	private final By itemPrice = By.cssSelector("[data-test='inventory-item-price']");
	
	@FindBy(css = ".summary_subtotal_label")
	private WebElement itemTotal;
	
	@FindBy(css = ".summary_tax_label")
	private WebElement taxAmount;

	public List<BigDecimal> getAllItemPrices() {
    	List<BigDecimal> prices = new ArrayList<BigDecimal>();
		List<WebElement> rows = driver.findElements(cartItems);

		for(int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			String priceText = row.findElement(itemPrice).getText();
			prices.add(parseMoney(priceText));
		}
		return prices;
    }
	
	public BigDecimal getItemTotal() {
		String totalText = itemTotal.getText();
		// "Item total: $29.99" -> "29.99"
		String cleaned = totalText.replace("Item total: ", "").trim();
		return parseMoney(cleaned);
	}
	
	public BigDecimal getTaxAmount() {
		String taxText = taxAmount.getText();
		// "Tax: $2.40" -> "2.40"
		String cleaned = taxText.replace("Tax: ", "").trim();
		return parseMoney(cleaned);
	}

	private BigDecimal parseMoney(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Price text was null");
		}

		// "$29.99" -> "29.99"
		String cleaned = text.trim().replace("$", "");
		return new BigDecimal(cleaned);
	}
}
