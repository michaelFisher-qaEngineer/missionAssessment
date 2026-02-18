package framework.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage {

	public ShoppingCartPage() {
		super();
	}
	
	@FindBy(css = "#checkout")
	private WebElement checkoutButton;
	
	private By inventoryItems = By.cssSelector("[data-test='inventory-item']");
	private By itemName = By.cssSelector("[data-test='inventory-item-name']");
	private By itemQuantity = By.cssSelector("[data-test='item-quantity']");

	public int getQuantityForProduct(String productName) {
	    List<WebElement> items = driver.findElements(inventoryItems);

	    for (WebElement item : items) {
	        String name = item.findElement(itemName).getText();

	        if (name.equalsIgnoreCase(productName)) {
	            return Integer.parseInt(
	                item.findElement(itemQuantity).getText()
	            );
	        }
	    }
	    throw new RuntimeException("Product not found: " + productName);
	}

	public List<Integer> getAllProductQuantities() {
	    List<WebElement> items = driver.findElements(inventoryItems);
	    List<Integer> quantities = new ArrayList<>();

	    for (WebElement item : items) {
	        int quantity = Integer.parseInt(
	            item.findElement(itemQuantity).getText()
	        );
	        quantities.add(quantity);
	    }
	    return quantities;
	}
	
	public void removeItemFromCart(String productName) {
	    List<WebElement> items = driver.findElements(inventoryItems);

	    for (WebElement item : items) {
	        String name = item.findElement(itemName).getText();

	        if (name.equalsIgnoreCase(productName)) {
	            item.findElement(By.cssSelector("button[data-test^='remove']")).click();
	            return;
	        }
	    }
	    throw new RuntimeException("Product not found: " + productName);
	}
	
	public int getTotalItemsInCart() {
	    List<WebElement> items = driver.findElements(inventoryItems);
	    int total = 0;

	    for (WebElement item : items) {
	        int quantity = Integer.parseInt(
	            item.findElement(itemQuantity).getText()
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
