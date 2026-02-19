package framework.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage {

	public ShoppingCartPage() {
		super();
	}
	
	@FindBy(css = "#checkout")
	private WebElement checkoutButton;
	
	@FindBy(css = "[data-test='inventory-item']")
    private List<WebElement> inventoryItems;
	
	private static final By ITEM_NAME = By.cssSelector("[data-test='inventory-item-name']");
	private static final By ITEM_QUANTITY = By.cssSelector("[data-test='item-quantity']");
	private static final By REMOVE_BUTTON = By.cssSelector("button[data-test^='remove']");
	
	public int getQuantityForProduct(String productName) {
		String target = productName == null ? "" : productName.trim();

	    for (WebElement item : inventoryItems) {
	        String name = item.findElement(ITEM_NAME).getText();

	        if (name.equalsIgnoreCase(target)) {
	            return Integer.parseInt(
	                item.findElement(ITEM_QUANTITY).getText()
	            );
	        }
	    }
	    throw new NoSuchElementException("Product not found: " + productName);
	}

	public List<Integer> getAllProductQuantities() {

	    List<Integer> quantities = new ArrayList<>();

	    for (WebElement item : inventoryItems) {
	        int quantity = Integer.parseInt(
	            item.findElement(ITEM_QUANTITY).getText()
	        );
	        quantities.add(quantity);
	    }
	    return quantities;
	}
	
	public void removeItemFromCart(String productName) {
		String target = productName == null ? "" : productName.trim();

	    for (WebElement item : inventoryItems) {
	        String name = item.findElement(ITEM_NAME).getText();

	        if (name.equalsIgnoreCase(target)) {
	            item.findElement(REMOVE_BUTTON).click();
	            return;
	        }
	    }
	    throw new NoSuchElementException("Product not found: " + productName);
	}
	
	public int getTotalItemsInCart() {
	    int total = 0;

	    for (WebElement item : inventoryItems) {
	        int quantity = Integer.parseInt(
	            item.findElement(ITEM_QUANTITY).getText()
	        );
	        total += quantity;
	    }
	    return total;
	}
	
	public void clickCheckoutButton() {
		checkoutButton.click();
	}
	
	public void openCheckout() {
		clickCheckoutButton();
	}
	
}
