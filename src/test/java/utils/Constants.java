package utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<String, String>  HEADER_ACCEPT_JSON = new HashMap<>() {
        {
            put("Accept", "application/json");
        }
    };

    public static final String TOKEN = "YWRtaW46cGFzc3dvcmQxMjM=";
    public static final String NOT_FOUND = "Not Found";
}
