package com.app.util.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    public static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private DateHelper() {
        // Empty Constructor
    }

    public static String formatDateTime(LocalDateTime dt, DateTimeFormatter format) {
        if (dt != null) {
            return dt.format(format);
        }
        return null;
    }

    public static String formatDateTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(DT_FORMATTER);
        }
        return null;
    }

    public static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, DT_FORMATTER);
    }
}
