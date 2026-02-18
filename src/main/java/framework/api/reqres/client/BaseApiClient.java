package framework.api.reqres.client;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import framework.config.LoadProp;

public class BaseApiClient {
	private static final Logger log = LogManager.getLogger(BaseApiClient.class);
    protected RequestSpecification request;
    private static final String BASE_URI = "https://reqres.in";
    private static final String API_KEY_PROPERTY = "reqresApiKey";


    public BaseApiClient() {
        log.info("Initializing ReqRes API client.");
    	LoadProp.validateRequiredKeys("reqresApiKey");
        this.request = buildRequestSpecification();
    }

    private RequestSpecification buildRequestSpecification() {
        String apiKey = getApiKey();
        log.debug("Building RequestSpecification. baseUri={}", BASE_URI);

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

            log.debug("API key loaded successfully from config.");
            return apiKey;

        } catch (Exception e) {
            log.error("Failed to load API key from config.", e);
            throw new RuntimeException("Failed to load API key from config", e);
        }
    }

}
