package api.reqres;

import io.restassured.specification.RequestSpecification;
import utilities.LoadProp;

import static io.restassured.RestAssured.given;

public class BaseClientPage {
    protected RequestSpecification request;
    private static final String BASE_URI = "https://reqres.in";
    private static final String API_KEY_PROPERTY = "REQRES_API_KEY";


    public BaseClientPage() {
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
