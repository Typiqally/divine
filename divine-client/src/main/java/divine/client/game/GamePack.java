package divine.client.game;

import divine.client.http.HttpUserAgent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Typically
 * Project: divine
 * Date: 1/29/2019
 */
public class GamePack {

    private static final String GAME_URL_BASE = "https://oldschool[world].runescape.com/";

    private final int world;
    private final Config config;
    private File file;
    private URLClassLoader classLoader;

    public GamePack(int world, File file) throws IOException {
        this.world = world;
        this.file = file;
        this.config = new Config(getUrl());
    }

    public void build() throws IOException {
        setup();

        if (world > 0)
            download();

        classLoader = new URLClassLoader(new URL[]{getFile().toURI().toURL()});
    }

    private void setup() throws IOException {
        File dir = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\")));
        if (!dir.exists() && !dir.mkdirs())
            throw new IOException();

        if (!file.exists() && !file.createNewFile())
            throw new IOException();
    }

    private void download() throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(getJarUrl().openStream()); FileOutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, count);
            }
        }
    }


    //TODO: temp fix
    public String getUrlFormatted() {
        int world = getWorld();
        if (world <= 0)
            world = 1;
        return GAME_URL_BASE.replaceFirst("\\[world]", String.valueOf(world));
    }

    public URL getUrl() throws MalformedURLException {
        return new URL(getUrlFormatted());
    }

    public URL getJarUrl() throws MalformedURLException {
        return new URL(getUrlFormatted() + config.getArchive());
    }

    public int getWorld() {
        return world;
    }

    public File getFile() {
        return file;
    }

    public Config getConfig() {
        return config;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    //TODO: REWRITE TO JAV_CONFIG.WS
    public class Config {

        private final HashMap<String, String> requestProperties = HttpUserAgent.getDefault();

        private final Pattern codePattern = Pattern.compile("code=(.*) ");
        private final Pattern archivePattern = Pattern.compile("archive=(.*) ");
        private final Pattern parameterPattern = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");

        private final URL url;
        private final String source;

        private String code;
        private String archive;
        private HashMap<String, String> parameters = new HashMap<>();

        Config(URL url) throws IOException {
            this.url = url;
            this.source = getSource();

            load();
        }

        private void load() throws IOException {
            code = matchRegex(source, codePattern).replace(".class", "");
            archive = matchRegex(source, archivePattern);
            parameters = matchParametersRegex(source);
        }

        private URLConnection openConnection() throws IOException {
            URLConnection connection = url.openConnection();

            for (Map.Entry<String, String> requestProperty : requestProperties.entrySet()) {
                connection.addRequestProperty(requestProperty.getKey(), requestProperty.getValue());
            }

            return connection;
        }

        public String matchRegex(String source, Pattern pattern) {
            Matcher matcher = pattern.matcher(source);

            if (matcher.find())
                return matcher.group(1);

            return null;
        }

        public HashMap<String, String> matchParametersRegex(String source) {
            HashMap<String, String> parameters = new HashMap<>();

            Matcher matcher = parameterPattern.matcher(source);
            while (matcher.find())
                parameters.put(matcher.group(1), matcher.group(2));

            return parameters;
        }

        public String getSource() throws IOException {
            URLConnection connection = openConnection();

            byte[] buffer = new byte[connection.getContentLength()];
            try (DataInputStream stream = new DataInputStream(connection.getInputStream())) {
                stream.readFully(buffer);
            }

            return new String(buffer);
        }

        public String getCode() {
            return code;
        }

        public String getArchive() {
            return archive;
        }

        public HashMap<String, String> getParameters() {
            return parameters;
        }
    }

}
