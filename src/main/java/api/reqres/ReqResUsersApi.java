package api.reqres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class ReqResUsersApi extends BaseClientPage {

	public UsersListResponse getUsersPage(int page) {
		Response r = given()
				.spec(request)
				.queryParam("page", page)
				.log().uri()
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.extract()
				.response();
		Integer actualPage = r.path("page");
	    if (actualPage == null) {
	        throw new RuntimeException("No 'page' field in response: " + r.asString());
	    }
	    if (actualPage.intValue() != page) {
	        throw new AssertionError("Requested page " + page + " but response says page " + actualPage +
	                ". Full response: " + r.asString());
	    }

		return UsersListResponse.from(r);
	}

	public UsersListResponse getAllUsers() {
		Response r = request
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.extract()
				.response();

		return UsersListResponse.from(r);
	}

	public int getTotalUserIdCount() {
		Response r = request
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.extract()
				.response();

		return r.path("data.id.size()");
	}

	public int getIdCountForPage(int page) {
		Response r = request
				.queryParam("page", page)
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.extract()
				.response();

		return r.path("data.id.size()");
	}

	public int getTotalIdCountForAllPages() {
		Response r = request
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.extract()
				.response();
		int totalUsers = 0;
		int pageCount = r.path("total_pages");
		for (int page = 1; page <= pageCount; page++) {
			Response pageResponse = request
					.queryParam("page", page)
					.when()
					.get("/api/users")
					.then()
					.statusCode(200)
					.extract()
					.response();
			int idCount = pageResponse.path("data.id.size()");
			totalUsers += idCount;
		}
		return totalUsers;
	}

	public UserGetResponse getUserData(int userId) {
		Response r = request
				.pathParam("id", userId)
				.when()
				.get("/api/users/{id}")
				.andReturn();

		return UserGetResponse.from(r);
	}
	
	public UserPostResponse createUserPost(String name, String job) {
		Response r = request
	            .contentType("application/json; charset=UTF-8") 
				.body("{\"name\": \"" + name + "\", \"job\": \"" + job + "\"}")
				.when()
				.post("/api/users")
				.andReturn();
		return UserPostResponse.from(r);
	}
	
	public LoginResponse userLogin(String email, String password) {
		Map<String, String> body = new HashMap<String, String>();
		body.put("email", email);
		body.put("password", password);

		Response r = request
				.contentType("application/json; charset=UTF-8")
				.body(body)
				.when()
				.post("/api/login")
				.andReturn();
		return LoginResponse.from(r);
	}
	
	public List<Integer> getAllUserIds() {

	    List<Integer> allIds = new ArrayList<Integer>();

	    UsersListResponse firstPage = getUsersPage(1);
	    allIds.addAll(firstPage.getUserIds());

	    int totalPages = firstPage.getTotalPages();

	    for (int page = 2; page <= totalPages; page++) {
	        UsersListResponse nextPage = getUsersPage(page);
	        allIds.addAll(nextPage.getUserIds());
	    }

	    return allIds;
	}

	
}
