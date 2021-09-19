package com.app.manager;

import com.app.util.log.AppLogger;

public class BaseManager {

    protected AppLogger log;

    protected AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz);
    }

    protected void start(String id, String methodName) {
        log.debug(id, methodName, "Start");
    }

    protected void completed(String id, String methodName) {
        log.debug(id, methodName, "Completed");
    }

    protected void start(String methodName) {
        log.debug(methodName, "Start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "Completed");
    }

    protected String getProp(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }
}
