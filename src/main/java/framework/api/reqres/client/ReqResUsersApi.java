package framework.api.reqres.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import framework.api.reqres.models.LoginResponse;
import framework.api.reqres.models.UserGetResponse;
import framework.api.reqres.models.UserPostResponse;
import framework.api.reqres.models.UsersListResponse;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class ReqResUsersApi extends BaseApiClient {
	private static final Logger log = LogManager.getLogger(ReqResUsersApi.class);
	public UsersListResponse getUsersPage(int page) {
	    log.info("GET /api/users?page={}", page);
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
	    log.info("GET /api/users?page={} -> status={}", page, r.getStatusCode());
	    log.debug("Response body: {}", r.asString());
	    
		Integer actualPage = r.path("page");
	    if (actualPage == null) {
	        log.error("Missing 'page' field in response. Body={}", r.asString());
	        throw new RuntimeException("No 'page' field in response: " + r.asString());
	    }
	    if (actualPage.intValue() != page) {
	        log.error("Requested page {} but response says {}. Body={}",
	                page, actualPage, r.asString());
	        throw new AssertionError("Requested page " + page + " but response says page " + actualPage +
	                ". Full response: " + r.asString());
	    }

		return UsersListResponse.from(r);
	}

	
	public UsersListResponse getAllUsers() {
		log.info("GET /api/users");
		Response r = request
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.extract()
				.response();

		log.info("GET /api/users -> status={}", r.getStatusCode());
	    log.debug("Response body: {}", r.asString());
		return UsersListResponse.from(r);
	}

	public int getTotalUserIdCount() {
		log.info("GET /api/users");
		Response r = request
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.extract()
				.response();
		log.info("GET /api/users -> status={}", r.getStatusCode());
	    log.debug("Response body: {}", r.asString());
	    
	    Integer count = r.path("data.id.size()");
	    if (count == null) {
	        log.error("Could not extract user ID count from response. Body={}", r.asString());
	        throw new RuntimeException("Failed to extract user ID count from response");
	    }
	    log.info("Total user ID count={}", count);

		return count;
	}

	public int getTotalIdCountForAllPages() {

	    log.info("GET /api/users (initial request to determine total pages)");

	    Response r = request
	            .when()
	            .get("/api/users")
	            .then()
	            .statusCode(200)
	            .extract()
	            .response();

	    log.info("GET /api/users -> status={}", r.getStatusCode());
	    log.debug("Initial response body: {}", r.asString());

	    Integer pageCount = r.path("total_pages");

	    if (pageCount == null) {
	        log.error("Could not determine total pages from response. Body={}", r.asString());
	        throw new RuntimeException("Failed to extract total_pages from response");
	    }

	    log.info("Total pages found={}", pageCount);

	    int totalUsers = 0;

	    for (int page = 1; page <= pageCount; page++) {

	        log.info("GET /api/users?page={}", page);

	        Response pageResponse = request
	                .queryParam("page", page)
	                .when()
	                .get("/api/users")
	                .then()
	                .statusCode(200)
	                .extract()
	                .response();

	        log.info("GET /api/users?page={} -> status={}", page, pageResponse.getStatusCode());
	        log.debug("Page {} response body: {}", page, pageResponse.asString());

	        Integer idCount = pageResponse.path("data.id.size()");

	        if (idCount == null) {
	            log.error("Could not extract ID count for page {}. Body={}", page, pageResponse.asString());
	            throw new RuntimeException("Failed to extract ID count for page " + page);
	        }

	        log.info("Page {} user count={}", page, idCount);

	        totalUsers += idCount;
	    }

	    log.info("Total users across all pages={}", totalUsers);

	    return totalUsers;
	}


	public UserGetResponse getUserData(int userId) {
		log.info("GET /api/users/{}", userId);

		Response r = request
				.pathParam("id", userId)
				.when()
				.get("/api/users/{id}")
				.andReturn();
	    log.info("GET /api/users/{} -> status={}", userId, r.getStatusCode());
	    log.debug("Response body: {}", r.asString());
	    if (r.getStatusCode() != 200) {
	        log.error("Unexpected status for GET /api/users/{}. status={}, body={}",
	                userId, r.getStatusCode(), r.asString());
	    }
		return UserGetResponse.from(r);
	}
	
	public UserPostResponse createUserPost(String name, String job) {
	    log.info("POST /api/users name={}, job={}", name, job);

		Response r = request
	            .contentType("application/json; charset=UTF-8") 
				.body("{\"name\": \"" + name + "\", \"job\": \"" + job + "\"}")
				.when()
				.post("/api/users")
				.andReturn();
	    log.info("POST /api/users -> status={}", r.getStatusCode());
	    log.debug("Response body: {}", r.asString());

	    if (r.getStatusCode() != 201) {
	        log.error("Unexpected status for POST /api/users. status={}, body={}",
	                r.getStatusCode(), r.asString());
	    }

	    return UserPostResponse.from(r);
	}
	
	public LoginResponse userLogin(String email, String password) {
	    log.info("POST /api/login email={}", email);

		Map<String, String> body = new HashMap<String, String>();
		body.put("email", email);
		body.put("password", password);

		Response r = request
				.contentType("application/json; charset=UTF-8")
				.body(body)
				.when()
				.post("/api/login")
				.andReturn();
		
	    log.info("POST /api/login -> status={}", r.getStatusCode());
	    log.debug("Response body: {}", r.asString());

	    if (r.getStatusCode() != 200) {
	        log.error("Login failed for email={}. status={}, body={}",
	                email, r.getStatusCode(), r.asString());
	    }

	    return LoginResponse.from(r);
	}
	
	public List<Integer> getAllUserIds() {
	    log.info("Fetching all user IDs across all pages");

	    List<Integer> allIds = new ArrayList<Integer>();

	    UsersListResponse firstPage = getUsersPage(1);
	    allIds.addAll(firstPage.getUserIds());

	    int totalPages = firstPage.getTotalPages();
	    log.info("Total pages found={}", totalPages);

	    for (int page = 2; page <= totalPages; page++) {
	        log.debug("Processing page {}", page);
	        UsersListResponse nextPage = getUsersPage(page);
	        allIds.addAll(nextPage.getUserIds());
	    }
	    log.info("Total user IDs collected={}", allIds.size());
	    log.debug("Collected user IDs={}", allIds);

	    return allIds;
	}
	
}
