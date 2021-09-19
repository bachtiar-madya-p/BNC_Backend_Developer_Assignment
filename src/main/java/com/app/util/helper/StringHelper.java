package com.app.util.helper;

import com.app.util.log.AppLogger;
import org.springframework.util.Base64Utils;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class StringHelper {
    private static final AppLogger log = new AppLogger(StringHelper.class);

    private StringHelper() {
        // Empty Constructor
    }

    public static boolean isValid(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static String urlEncode(String str) {
        try {
            return UriUtils.encode(str, "UTF-8");
        } catch (IllegalArgumentException ex) {
            log.error("urlEncode", ex);
        }
        return str;
    }

    public static String urlDecode(String str) {
        try {
            return UriUtils.decode(str, "UTF-8");
        } catch (IllegalArgumentException ex) {
            log.error("urlDecode", ex);
        }
        return str;
    }

    public static String base64Encode(String str) {
        return new String(Base64Utils.encode(str.getBytes()), StandardCharsets.UTF_8);
    }

    public static String base64Encode(byte[] bytes) {
        return new String(Base64Utils.encode(bytes));
    }

    public static String base64Decode(String str) {
        return new String(Base64Utils.decode(str.getBytes(StandardCharsets.UTF_8)));
    }

    public static String base64Decode(byte[] bytes) {
        return new String(Base64Utils.decode(bytes));
    }

    public static String toUpperCase(String str) {
        return str.toUpperCase();
    }

    public static boolean isParamValid(String param) {
        return param != null && !param.trim().isEmpty();
    }

    public static boolean isIntParamValid(String param) {
        return isParamValid(param) && param.matches("^[0-9]+$");
    }

    public static boolean isCodeParamValid(String param) {
        return isParamValid(param) && param.matches("^[a-zA-Z0-9-]+$") && param.length()<=20;
    }

    public static <T> boolean isParamListValid(List<T> list) {
        return list != null && !list.isEmpty();
    }

    public static String generateTransactionId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }

}
