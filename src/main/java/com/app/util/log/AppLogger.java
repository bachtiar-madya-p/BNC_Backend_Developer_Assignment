package com.app.util.log;

import com.app.util.json.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLogger {
    protected final Logger log;
    protected LogLevel level;

    public AppLogger(Class<?> clazz) {
        log = LogManager.getLogger(clazz);
        level = getLogLevel();
    }

    // DEBUG METHODS
    public void debug(String text) {
        if (allow(LogLevel.DEBUG)) {
            log.debug(text);
        }
    }

    public void debug(String methodName, String text) {

        final boolean display = allow(LogLevel.DEBUG);

        if (display) {
            log.debug(() -> format(methodName, text));
        }
    }

    public void debug(String methodName, Object obj) {
        if (allow(LogLevel.DEBUG)) {
            log.debug(() -> format(methodName, JsonHelper.toJson(obj)));
        }
    }

    public void debug(String id, String methodName, String text) {
        if (allow(LogLevel.DEBUG)) {
            log.debug(() -> format(id, methodName, text));
        }
    }

    public void debug(String id, String methodName, Object obj) {
        if (allow(LogLevel.DEBUG)) {
            log.debug(() -> format(id, methodName, JsonHelper.toJson(obj)));
        }
    }

    // INFO METHODS
    public void info(String text) {
        if (allow(LogLevel.INFO)) {
            log.info(text);
        }
    }

    public void info(String methodName, String text) {
        if (allow(LogLevel.INFO)) {
            log.info(() -> format(methodName, text));
        }
    }

    public void info(String methodName, Object obj) {
        if (allow(LogLevel.INFO)) {
            log.debug(() -> format(methodName, JsonHelper.toJson(obj)));
        }
    }

    public void info(String id, String methodName, String text) {
        if (allow(LogLevel.INFO)) {
            log.debug(() -> format(id, methodName, text));
        }
    }

    public void info(String id, String methodName, Object obj) {
        if (allow(LogLevel.INFO)) {
            log.debug(() -> format(id, methodName, JsonHelper.toJson(obj)));
        }
    }

    // WARN METHODS
    public void warn(String text) {
        if (allow(LogLevel.WARN)) {
            log.warn(text);
        }
    }

    public void warn(String methodName, String text) {
        if (allow(LogLevel.WARN)) {
            log.warn(() -> format(methodName, text));
        }
    }

    public void warn(String methodName, Object obj) {
        if (allow(LogLevel.WARN)) {
            log.warn(() -> format(methodName, JsonHelper.toJson(obj)));
        }
    }

    public void warn(String id, String methodName, String text) {
        if (allow(LogLevel.WARN)) {
            log.warn(() -> format(id, methodName, text));
        }
    }

    public void warn(String id, String methodName, Object obj) {
        if (allow(LogLevel.WARN)) {
            log.warn(() -> format(id, methodName, JsonHelper.toJson(obj)));
        }
    }

    // ERROR METHODS
    public void error(String methodName, String text) {
        if (allow(LogLevel.ERROR)) {
            log.error(() -> format(methodName, text));
        }
    }

    public void error(String methodName, Object obj) {
        if (allow(LogLevel.ERROR)) {
            log.error(() -> format(methodName, JsonHelper.toJson(obj)));
        }
    }

    public void error(String methodName, String text, Exception ex) {
        if (allow(LogLevel.ERROR)) {
            log.error(() -> format(methodName, text), ex);
        }
    }

    public void error(String id, String methodName, Object obj) {
        if (allow(LogLevel.ERROR)) {
            log.error(() -> format(id, methodName, JsonHelper.toJson(obj)));
        }
    }

    public void error(String id, String methodName, String text, Exception ex) {
        if (allow(LogLevel.ERROR)) {
            log.error(() -> format(id, methodName, text), ex);
        }
    }

    private String format(String id, String methodName, String text) {
        return "[" + id + "] [" + methodName + "] " + text;
    }

    private String format(String methodName, String text) {
        return "[" + methodName + "] " + text;
    }

    private boolean allow(LogLevel msgLevel) {
        return msgLevel.getValue() >= level.getValue();
    }

    private LogLevel getLogLevel() {
        return LogLevel.DEBUG;
    }
}
