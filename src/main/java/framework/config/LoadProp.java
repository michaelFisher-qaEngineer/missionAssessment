package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProp {

	private static Properties prop;
	public static String testData = "testdata/TestData.properties";
	
    private LoadProp() {
        // prevent instantiation
    }

    public static synchronized String getProperty(String key) {

		if (prop == null) {
			prop = new Properties();

			try {
				InputStream is = LoadProp.class.getClassLoader().getResourceAsStream(testData);
				if (is == null) {
					throw new RuntimeException("Could not find " + testData + " on the classpath");
				}
				prop.load(is);
			} catch (IOException e) {
				throw new RuntimeException("Failed to load properties file: " + testData, e);
			}
		}
		return prop.getProperty(key);
	}
}
