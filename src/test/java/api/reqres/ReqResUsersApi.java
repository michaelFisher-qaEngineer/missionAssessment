package api.reqres;

import io.restassured.response.Response;

public class ReqResUsersApi extends BaseClientPage {

	public UsersListResponse getUsersPage(int page) {
		Response r = request
				.queryParam("page", page)
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.extract()
				.response();

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
	
}
