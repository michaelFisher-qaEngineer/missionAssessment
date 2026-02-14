package stepDefs;

import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;

public class StepDefinition  {

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        HomePage homePage = new HomePage();
        homePage.open();
    }
    
    @And("I login in with the following details")
    public void i_login_in_with_the_following_details(DataTable dataTable) {
        List<Map<String, String>> credentials = dataTable.asMaps(String.class, String.class);
        String userName = credentials.get(0).get("userName");
        String password = credentials.get(0).get("Password");
        HomePage homePage = new HomePage();
        homePage.enterUserName(userName);
        homePage.enterPassword(password);
        homePage.clickLogin();

    }

    @Given("^I get the default list of users for on 1st page$")
    public void iGetTheDefaultListofusers() {
    }

    @When("I get the list of all users within every page")
    public void iGetTheListOfAllUsers() {
    }

    @Then("I should see total users count equals the number of user ids")
    public void iShouldMatchTotalCount() {

    }

    @Given("I make a search for user (.*)")
    public void iMakeASearchForUser(String sUserID) {

    }

    @Then("I should see the following user data")
    public void IShouldSeeFollowingUserData(DataTable dt) {
    }

    @Then("I receive error code (.*) in response")
    public void iReceiveErrorCodeInResponse(int responseCode) {

    }

    @Given("I create a user with following (.*) (.*)")
    public void iCreateUserWithFollowing(String sUsername, String sJob) {
    }

    @Then("response should contain the following data")
    public void iReceiveErrorCodeInResponse(DataTable dt) {

    }

    @Given("I login unsuccessfully with the following data")
    public void iLoginSuccesfullyWithFollowingData(DataTable dt) {

    }

    @Given("^I wait for the user list to load$")
    public void iWaitForUserListToLoad() {

    }

    @Then("I should see that every user has a unique id")
    public void iShouldSeeThatEveryUserHasAUniqueID() {

        // Please not that we need to check all values are unique in the list.
    }

    @Then("^I should get a response code of (\\d+)$")
    public void iShouldGetAResponseCodeOf(int responseCode) {
    }

    @And("^I should see the following response message:$")
    public void iShouldSeeTheFollowingResponseMessage() {
    }
}
