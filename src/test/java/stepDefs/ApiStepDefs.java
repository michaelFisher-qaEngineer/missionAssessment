package stepDefs;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import api.reqres.LoginResponse;
import api.reqres.ReqResUsersApi;
import api.reqres.UserGetResponse;
import api.reqres.UserPostResponse;
import api.reqres.UsersListResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static utilities.Verifications.*;

public class ApiStepDefs {
	private final ReqResUsersApi usersApi = new ReqResUsersApi();

	private UsersListResponse firstPageResponse;
	private UsersListResponse numberPageResponse;
	private int totalUsers;
	private List<Integer> allUserIds;

	private static UserGetResponse userResponse;
	private static UserPostResponse userPostResponse;
	private static LoginResponse loginResponse;

	private final Set<Integer> uniqueUserIds = new HashSet<Integer>();

	@Given("^I get the default list of users for on 1st page$")
	public void iGetTheDefaultListofusers() {
		firstPageResponse = usersApi.getUsersPage(1);
		totalUsers = firstPageResponse.getTotal();

		List<Integer> userIds = firstPageResponse.getUserIds();
		if (userIds != null) {
			uniqueUserIds.addAll(userIds);
		}
	}

	@Given("I get the default list of users on {int} page")
	public void iGetTheDefaultListOfUsersOnPage(int page) {
		numberPageResponse = usersApi.getUsersPage(page);
		totalUsers = numberPageResponse.getTotal();

		List<Integer> userIds = numberPageResponse.getUserIds();
		if (userIds != null) {
			uniqueUserIds.addAll(userIds);
		}
	}

	@When("I get the list of all users within every page")
	public void iGetTheListOfAllUsers() {
		totalUsers = usersApi.getAllUsers().getTotal();
	}

	@Then("I should see total users count equals the number of user ids")
	public void iShouldMatchTotalCount() {
		int totalUserIdCount = usersApi.getTotalIdCountForAllPages();
		verifyEqualsInt(totalUserIdCount, totalUsers, "Total user ID count should match total users");

	}

	@Given("I make a search for userId {int}")
	public void iMakeASearchForUser(int userId) {
		userResponse = usersApi.getUserData(userId);
	}

	@Then("I should see the following user data")
	public void IShouldSeeFollowingUserData(DataTable dt) {
		List<Map<String, String>> rows = dt.asMaps(String.class, String.class);

		if (rows.isEmpty()) {
			throw new IllegalArgumentException("DataTable is empty");
		}

		Map<String, String> expected = rows.get(0);

		for (Entry<String, String> entry : expected.entrySet()) {

			String field = entry.getKey();
			String expectedValue = entry.getValue();
			if (expectedValue == null) {
				continue;
			}

			switch (field.toLowerCase()) {
			case "id":
				verifyEqualsInt(userResponse.getId(), Integer.parseInt(expectedValue),
						"User ID should match");
				break;

			case "email":
				verifyEqualsString(userResponse.getEmail(), expectedValue, "User email should match");
				break;

			case "first_name":
				verifyEqualsString(userResponse.getFirstName(), expectedValue,
						"User first name should match");
				break;

			default:
				throw new IllegalArgumentException("Unknown field: " + field);

			}
		}
	}

	@Then("I receive error code {int} in response")
	public void iReceiveErrorCodeInResponse(int responseCode) {
		verifyEqualsInt(userResponse.getStatusCode(), responseCode,
				"Response status code should match expected error.");
	}

	@Given("I create a user with following {string} {string}")
	public void iCreateAUserWithFollowing(String name, String job) {
		userPostResponse = usersApi.createUserPost(name, job);
	}

	@Then("response should contain the following data")
	public void responseShouldContainTheFollowingData(DataTable dt) {
		List<String> fields = dt.asList(String.class);

		if (fields.isEmpty()) {
			throw new IllegalArgumentException("DataTable is empty");
		}

		for (String field : fields) {

			switch (field.toLowerCase()) {
			case "name":
				verifyNotNullOrEmpty(userPostResponse.getName(), "name should be present in response.");
				break;
			case "id":
				// Verify that ID is not null/empty for created user
				verifyNotNull(userPostResponse.getId(), "User ID should be generated");
				break;
			case "job":
				verifyNotNullOrEmpty(userPostResponse.getJob(), "job should be present in response.");
				break;
			case "createdat":
				// Verify that createdAt timestamp is not null/empty for created user
				verifyNotNull(userPostResponse.getCreatedAt(), "CreatedAt timestamp should be generated");
				break;
			default:
				throw new IllegalArgumentException("Unknown field: " + field);
			}
		}
	}
	@Given("I login unsuccessfully with the following data")
	public void iLoginUnsuccesfullyWithFollowingData(DataTable dt) {
		iLoginSuccessfullyWithFollowingData(dt);
	}
	
	@Given("I login successfully with the following data")
	public void iLoginSuccessfullyWithFollowingData(DataTable dt) {
		List<Map<String, String>> rows = dt.asMaps(String.class, String.class);
		if (rows.isEmpty()) {
			throw new IllegalArgumentException("DataTable is empty");
		}
		Map<String, String> loginData = rows.get(0);
		String email = loginData.get("Email");
		String password = loginData.get("Password");
		loginResponse = usersApi.userLogin(email, password);
	}

	@Then("I should get a response code of {int}")
	public void iShouldGetAResponseCodeOf(int responseCode) {
		verifyEqualsInt(loginResponse.getStatusCode(), responseCode,
				"Login response status code should match expected.");
	}
	
	@And("I should see the following response message:")
	public void iShouldSeeTheFollowingResponseMessage(DataTable dt) {
	    List<Map<String, String>> rows = dt.asMaps(String.class, String.class);
	    if (rows.isEmpty()) {
	        throw new IllegalArgumentException("DataTable is empty");
	    }
	    
	    Map<String, String> expectedMessages = rows.get(0);
	    String field = expectedMessages.get("field");
	    String expectedValue = expectedMessages.get("value");
	    
	    if ("error".equalsIgnoreCase(field)) {
	        verifyEqualsString(loginResponse.getErrorMessage(), expectedValue,
	                "Error message should match expected value");
	    } else {
	        throw new IllegalArgumentException("Unsupported field: " + field);
	    }
	}
	
	@Given("I wait for the user list to load")
	public void iWaitForUserListToLoad() {
		allUserIds = usersApi.getAllUserIds();
	}
	
	@Then("I should see that every user has a unique id")
	public void iShouldSeeThatEveryUserHasAUniqueID() {
		verifyAllUnique(allUserIds, "User IDs should be unique");
	}

}
