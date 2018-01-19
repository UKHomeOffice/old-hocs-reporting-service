package uk.gov.digital.ho.hocs.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

class BaseProperties {

    protected String SetString(String key, Map<String, String> map) {
        String value = null;

        if (map != null) {
            String stringValue = map.getOrDefault(key, null);
            if (stringValue == null || (stringValue.equals("null") || stringValue.equals(""))) {
                value = null;
            } else {
                value = stringValue;
            }
        }
        return value;
    }

    protected Integer SetInt(String key, Map<String, String> map) {
        Integer value = null;

        if (map != null) {
            String stringValue = map.getOrDefault(key, null);
            if (stringValue == null || (stringValue.equals("null") || stringValue.equals(""))) {
                value = null;
            } else {
                value = Integer.parseInt(stringValue);
            }
        }
        return value;
    }

    protected Boolean SetBool(String key, Map<String, String> map) {
        Boolean value = null;

        if (map != null) {
            String stringValue = map.getOrDefault(key, null);
            if (stringValue == null || (stringValue.equals("null") || stringValue.equals(""))) {
                value = null;
            } else {
                value = Boolean.parseBoolean(stringValue);
            }
        }
        return value;
    }

    protected LocalDateTime SetDate(String key, Map<String, String> map) {
        LocalDateTime value = null;

        if (map != null) {
            String stringValue =  map.getOrDefault(key, null);
            if (stringValue == null || (stringValue.equals("null") || stringValue.equals(""))) {
                value = null;
            } else {
                value = LocalDateTime.parse(stringValue, DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH));
                }
        }
        return value;
    }
}