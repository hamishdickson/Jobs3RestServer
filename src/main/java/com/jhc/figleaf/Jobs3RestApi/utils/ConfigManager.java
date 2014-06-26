package com.jhc.figleaf.Jobs3RestApi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: DicksonH
 * Date: 13/03/14
 * Time: 11:26
 */
public class ConfigManager {

    private static final Map<String, String> settings;

    static {
        Map<String, String> settingsBuilder = new HashMap<String, String>();
        String location = getPropertiesLocation();

        Properties properties = new Properties();

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(location);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            settingsBuilder.put((String) entry.getKey(), (String)entry.getValue());
        }

        settings = Collections.unmodifiableMap(settingsBuilder);
    }

    public static String getSetting(String key)
    {
        return settings.get(key);
    }

    private static String getPropertiesLocation() {
        if (System.getProperty("os.name").toLowerCase().indexOf( "win" ) == 0) {
            return new File(ConfigManager.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParent() + "\\dicksonh\\Documents\\GitHub\\Jobs3RestServer\\src\\main\\webapp\\restapp.properties";
        } else {
            return "/opt/jhc/figleaf/restapp.properties";
        }
    }
}
