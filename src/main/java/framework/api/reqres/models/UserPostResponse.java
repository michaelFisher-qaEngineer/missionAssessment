package framework.api.reqres.models;

import io.restassured.response.Response;

public class UserPostResponse {

	private final String name;
	private final String job;
	private final String id;
	private final String createdAt;
	private final int statusCode;
	
	private UserPostResponse(String name, String job, String id, String createdAt, int statusCode) {
		this.statusCode = statusCode;
		this.name = name;
		this.job = job;
		this.id = id;
		this.createdAt = createdAt;
	}
	
	public static UserPostResponse from(Response r) {

        String name = r.path("name");
        String job = r.path("job");
        String id = r.path("id");
        String createdAt = r.path("createdAt");
        int statusCode = r.getStatusCode();

        return new UserPostResponse(
        		name,
                job,
                id,
                createdAt,
                statusCode
        );
    }

    public String getName() { return name;}
    public String getJob() { return job; }
    public String getId() { return id; }
    public String getCreatedAt() { return createdAt; }
    public int getStatusCode() { return statusCode; }
}
