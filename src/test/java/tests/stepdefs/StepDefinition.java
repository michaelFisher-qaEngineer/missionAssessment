package tests.stepdefs;

import static framework.verifications.Verifications.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import framework.pages.CheckoutOverviewPage;
import framework.pages.CheckoutPage;
import framework.pages.HomePage;
import framework.pages.ProductsPage;
import framework.pages.ShoppingCartPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinition {
    private HomePage homePage = new HomePage();
    private ProductsPage productsPage = new ProductsPage();
    private ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
    private CheckoutPage checkoutPage = new CheckoutPage();
    private CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage();

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        homePage.open();
    }
    
    @And("I login in with the following details")
    public void i_login_in_with_the_following_details(DataTable dataTable) {

        List<Map<String, String>> credentials = dataTable.asMaps(String.class, String.class);
        if (credentials == null || credentials.isEmpty()) {
            throw new RuntimeException("Login DataTable is empty. Expected a row with userName and Password.");
        }

        Map<String, String> row = credentials.get(0);

        String userName = row.get("userName");
        String passwordKey = row.get("Password"); // this is now a lookup key like "validPassword"

        if (userName == null || userName.trim().isEmpty()) {
            throw new RuntimeException("Login DataTable missing required column value: userName");
        }

        if (passwordKey == null || passwordKey.trim().isEmpty()) {
            throw new RuntimeException("Login DataTable missing required column value: Password (expected a password key like 'validPassword')");
        }

        String resolvedPassword = framework.config.LoadProp.getProperty(passwordKey);

        if (resolvedPassword == null || resolvedPassword.trim().isEmpty()) {
            throw new RuntimeException(
                "No password found for key '" + passwordKey + "' in properties. " +
                "Add it to your credentials properties (and keep that file out of git)."
            );
        }

        homePage.enterUserName(userName);
        homePage.enterPassword(resolvedPassword);
        homePage.clickLogin();
    }

    @And("I add the following items to the basket")
    public void i_add_the_following_items_to_the_basket(DataTable dataTable) {
        List<String> items = dataTable.asList(String.class);
        for (String item : items) {
            productsPage.addItemToCart(item);
        }
    }

    @And("I should see {int} items added to the shopping cart")
    public void i_should_see_items_added_to_the_shopping_cart(int expectedCount) {
        verifyEqualsInt(productsPage.getShoppingCartItemCount(), expectedCount, "Shopping cart item count");
    }

    @And("I click on the shopping cart")
    public void i_click_on_the_shopping_cart() {
        productsPage.openShoppingCart();
    }

    @And("I verify that the QTY count for each item should be {int}")
    public void i_verify_that_the_qty_count_for_each_item_should_be(int expectedQty) {
        List<Integer> quantitiesList = shoppingCartPage.getAllProductQuantities();
        verifyAllIntsEqual(quantitiesList, expectedQty, "Shopping cart item quantity");
    }

    @And("I remove the following item:")
    public void i_remove_the_following_item(DataTable dataTable) {
        List<String> items = dataTable.asList(String.class);
        for (String item : items) {
            shoppingCartPage.removeItemFromCart(item);
        }
    }

    @And("I click on the CHECKOUT button")
    public void i_click_on_the_checkout_button() {
        shoppingCartPage.openCheckout();
    }

    @And("I type {string} for First Name")
    public void i_type_for_first_name(String firstName) {
        checkoutPage.enterFirstName(firstName);
    }

    @And("I type {string} for Last Name")
    public void i_type_for_last_name(String lastName) {
        checkoutPage.enterLastName(lastName);
    }

    @And("I type {string} for Zip-Postal Code")
    public void typeForZipCode(String zipCode) {
        checkoutPage.enterPostalCode(zipCode);
    }

    @When("I click on the CONTINUE button")
    public void i_click_on_the_continue_button() {
        checkoutPage.clickContinue();
    }

    @Then("Item total will be equal to the total of items on the list")
    public void i_should_see_that_the_item_total_equals_the_sum_of_all_items() {
        List<BigDecimal> itemPrices = checkoutOverviewPage.getAllItemPrices();
        BigDecimal expectedTotal = BigDecimal.ZERO;

        for (BigDecimal price : itemPrices) {
            expectedTotal = expectedTotal.add(price);
        }

        BigDecimal actualTotal = checkoutOverviewPage.getItemTotal();
        verifyEqualsBigDecimal(actualTotal, expectedTotal, "Checkout overview item total");
    }

    @And("a Tax rate of {int} % is applied to the total")
    public void a_tax_rate_of_is_applied_to_the_total(int taxRate) {
        BigDecimal itemTotal = checkoutOverviewPage.getItemTotal();

        BigDecimal expectedTax = itemTotal
                .multiply(BigDecimal.valueOf(taxRate))
                .divide(BigDecimal.valueOf(100));

        BigDecimal actualTax = checkoutOverviewPage.getTaxAmount();
        verifyEqualsCurrency(actualTax, expectedTax, "Tax calculation");
    }

}
