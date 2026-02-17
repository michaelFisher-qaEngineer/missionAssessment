package api.reqres;

import io.restassured.response.Response;

public class UserGetResponse  {
    private final int id;
    private final int statusCode;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String createdAt;

    private UserGetResponse(int statusCode, int id, String email, String firstName, String lastName, String createdAt) {
        this.statusCode = statusCode;
    	this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        
    }

    public static UserGetResponse from(Response r) {
        int statusCode = r.getStatusCode();
        if(statusCode != 200) {
        	return new UserGetResponse(statusCode, -1, null, null, null, null);
        }
        //only parse the body if the status code is 200
        Integer id = r.path("data.id");
        String email = r.path("data.email");
        String firstName = r.path("data.first_name");
        String lastName = r.path("data.last_name");
        String createdAt = r.path("createdAt");
        return new UserGetResponse(
        		statusCode,
				id != null ? id.intValue() : -1, 
				email, 
				firstName, 
				lastName,
				createdAt);
        
    }

	    public int getId() { return id; }
	    public String getEmail() { return email; }
	    public String getFirstName() { return firstName; }
	    public String getLastName() { return lastName; }
	    public int getStatusCode() { return statusCode; }
	    public String getCreatedAt() { return createdAt; }

}
