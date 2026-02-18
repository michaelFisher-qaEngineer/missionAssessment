package framework.pages;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsPage extends BasePage {

	public ProductsPage() {
		super();
	}

	@FindBy(className = "inventory_item")
	private List<WebElement> productContainers;
	
	@FindBy(css=".shopping_cart_link")
	private WebElement shoppingCartLink;
	
	@FindBy(className = "shopping_cart_badge")
	private List<WebElement> cartBadges;

	private static final By PRODUCT_NAME = By.className("inventory_item_name");
	private static final By ADD_TO_CART_BUTTON = By.cssSelector("button[data-test^='add-to-cart']");

	public void addItemToCart(String itemName) {
		String target = itemName == null ? "" : itemName.trim();

		for (WebElement product : productContainers) {
			String name = product.findElement(PRODUCT_NAME).getText().trim();
			if (name.equalsIgnoreCase(target)) {
				product.findElement(ADD_TO_CART_BUTTON).click();
				return;
			}
		}
		throw new NoSuchElementException("Product not found: " + itemName);
	}
	
	public void openShoppingCart() {
		shoppingCartLink.click();
	}
	
	public int getShoppingCartItemCount() {
		if (cartBadges.isEmpty()) {
	        return 0;
	    }
	    String itemCountText = cartBadges.get(0).getText().trim();
	    return Integer.parseInt(itemCountText);
	}

}
