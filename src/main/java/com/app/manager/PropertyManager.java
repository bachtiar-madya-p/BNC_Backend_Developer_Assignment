package com.app.manager;

import com.app.util.property.Property;

import java.util.Properties;

public class PropertyManager extends BaseManager {

    private static PropertyManager instance;

    private Properties prop;

    private PropertyManager() {
        final String methodName = "Constructor";
        log = getLogger(this.getClass());
        start(methodName);

        prop = new Properties();
        try {

            log.debug(methodName, "Loading Environment Properties");
            prop.load(PropertyManager.class.getClassLoader().getResourceAsStream(Property.PROPERTY_FILENAME));

        } catch (Exception ex) {
            log.error("PropertyManager", "Error Loading Properties File", ex);
        }
        completed(methodName);
    }

    public String getProperty(String key) {
        return getProperty(key, "");
    }

    private String getProperty(String key, String defaultValue) {
        if (prop != null) {
            return prop.getProperty(key, defaultValue);
        }
        return defaultValue;
    }

    public void shutdown() {
        prop.clear();
    }

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }
}
