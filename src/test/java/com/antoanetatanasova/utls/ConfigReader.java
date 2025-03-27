package com.antoanetatanasova.utls;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    /**
     * Fetches the value of a given key from the {@code config.properties} file.
     * <p>
     * The method loads the properties file located at {@code ./config/config.properties}
     * and retrieves the value associated with the specified key.
     *
     * @param key the property key to look up
     * @return the value corresponding to the provided key, or {@code null} if the key is not found
     * @throws IOException if an error occurs while reading the properties file
     */
    public static String fetchProperty(String key) throws IOException {
        FileInputStream file = new FileInputStream("./config/config.properties");
        Properties property = new Properties();
        property.load(file);
        return property.getProperty(key);
    }

}
