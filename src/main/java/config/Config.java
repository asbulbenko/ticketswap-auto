package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties propertiesConfig() {
        FileReader reader = null;
        try {
            reader = new FileReader("src/main/resources/test.profile");
        } catch (FileNotFoundException e) {
            System.out.println("Resource test.profile not found log " + e);;
        }
        Properties prop = new Properties();
        try {
            prop.load(reader);
        } catch (IOException e) {
            System.out.println("Exception log " + e);
        }
        return prop;
    }

    public static String getEmail() {
        return propertiesConfig().getProperty("email");
    }

    public static String getAppUrl() {
        return propertiesConfig().getProperty("main_url");
    }

}
