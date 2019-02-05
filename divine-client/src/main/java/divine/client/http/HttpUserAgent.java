package divine.client.http;

import java.util.HashMap;

/**
 * Author: Typically
 * Project: divine
 * Date: 1/29/2019
 */
public class HttpUserAgent {

    private static final HashMap<String, String> DEFAULT = new HashMap<String, String>() {
        {
            put("Protocol", "HTTP/1.1");
            put("Connection", "keep-alive");
            put("Keep-Alive", "200");
            put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36 OPR/58.0.3135.47");
        }
    };

    public static HashMap<String, String> getDefault() {
        return DEFAULT;
    }
}
