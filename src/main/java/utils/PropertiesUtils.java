package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    public final static String TEST_DATA_PATH = "src/test/resources/TestData/";


    public static String getPropertyValue(String fileName, String key) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(TEST_DATA_PATH + fileName + ".properties"));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

