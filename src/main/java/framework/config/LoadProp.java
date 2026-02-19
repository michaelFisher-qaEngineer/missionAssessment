package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadProp {
	private static final Logger log = LogManager.getLogger(LoadProp.class);
	private static Properties prop;
	public static String TEST_DATA = "testdata/TestData.properties";
	
    private LoadProp() {
        // prevent instantiation
    }

    private static void loadIfNeeded() {
        if (prop == null) {
            prop = new Properties();

            try (InputStream inputStream = LoadProp.class
                    .getClassLoader()
                    .getResourceAsStream(TEST_DATA)) {

                if (inputStream == null) {
                    log.error("Properties file not found on classpath: {}", TEST_DATA);
                    throw new RuntimeException("Could not find " + TEST_DATA + " on classpath");
                }

                prop.load(inputStream);

                log.info("Loaded properties: {}", TEST_DATA);

            } catch (IOException e) {
                log.error("Failed to load properties file: {}", TEST_DATA, e);
                throw new RuntimeException("Failed to load properties file: " + TEST_DATA, e);
            }
        }
    }
    
    public static synchronized String getProperty(String key) {
    	loadIfNeeded();
    	String value = prop.getProperty(key);

        if (value == null) {
            throw new RuntimeException("Missing required property: " + key +
                    " in " + TEST_DATA);
        }

        return value.trim();
	}
    
    public static void validateRequiredKeys(String... keys) {
        loadIfNeeded();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            String val = prop.getProperty(key);
            if (val == null || val.trim().isEmpty()) {
                throw new RuntimeException("Missing required property: " + key + " in " + TEST_DATA);
            }
        }
    }
}
