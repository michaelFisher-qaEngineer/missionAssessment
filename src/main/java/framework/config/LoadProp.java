package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProp {

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
                    throw new RuntimeException("Could not find " + TEST_DATA + " on classpath");
                }

                prop.load(inputStream);

            } catch (IOException e) {
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
}
