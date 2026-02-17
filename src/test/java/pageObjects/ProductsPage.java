package pageObjects;

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

	private final By productName = By.className("inventory_item_name");
	private final By addToCartButton = By.cssSelector("button[data-test^='add-to-cart']");

	public void addItemToCart(String itemName) {

		for (WebElement product : productContainers) {
			String name = product.findElement(productName).getText().trim();
			if (name.equalsIgnoreCase(itemName.trim())) {
				product.findElement(addToCartButton).click();
				return;
			}
		}
		throw new NoSuchElementException("Product not found: " + itemName);
	}
	
	public void openShoppingCart() {
		shoppingCartLink.click();
	}
	
	public int getShoppingCartItemCount() {
		List<WebElement> cartBadge = shoppingCartLink.findElements(By.className("shopping_cart_badge"));
		if (cartBadge.size() == 0) {
			return 0;
		}
		String itemCountText = cartBadge.get(0).getText().trim();
		return Integer.parseInt(itemCountText);
	}

}
