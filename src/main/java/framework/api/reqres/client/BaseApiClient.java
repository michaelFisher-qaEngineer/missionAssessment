package framework.api.reqres.client;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import framework.config.LoadProp;

public class BaseApiClient {
    protected RequestSpecification request;
    private static final String BASE_URI = "https://reqres.in";
    private static final String API_KEY_PROPERTY = "reqresApiKey";


    public BaseApiClient() {
    	LoadProp.validateRequiredKeys("reqresApiKey");
        this.request = buildRequestSpecification();
    }

    private RequestSpecification buildRequestSpecification() {
        String apiKey = getApiKey();

        return given()
                .baseUri(BASE_URI)
                .header("x-api-key", apiKey)
                .header("Accept", "application/json");
    }

    private String getApiKey() {
        try {
            String apiKey = LoadProp.getProperty(API_KEY_PROPERTY);

            if (apiKey == null || apiKey.isBlank()) {
                throw new RuntimeException(API_KEY_PROPERTY + " not configured or empty");
            }

            return apiKey;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load API key from config", e);
        }
    }

}
