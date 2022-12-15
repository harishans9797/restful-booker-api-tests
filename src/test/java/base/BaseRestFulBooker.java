package base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseRestFulBooker {
    public String env = System.getProperty("env");
    private final InputStream environmentalPropFile = BaseRestFulBooker.class.getClassLoader().getResourceAsStream(env + ".properties");
    Properties properties = new Properties();

    protected void init () {
        try {
            this.properties.load(this.environmentalPropFile);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public String loadProp(String property) {
       return properties.getProperty(property);
    }
}
