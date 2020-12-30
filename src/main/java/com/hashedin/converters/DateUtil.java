package com.hashedin.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.regex.Pattern;

/**
 * Util class to handle date operations
 */
public class DateUtil {


    private static final String DATE_REGEX = "[0-9][0-9-:\\s]*";
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX, Pattern.MULTILINE);

    private static final DateTimeFormatter DEFAULT_DATETIME_FORMAT = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");
    private static final String TIME_IN_MIDNIGHT = " 00:00:00";


    public static LocalDateTime parse(String date) {
        if (date.length() == 10) {
            date = date + TIME_IN_MIDNIGHT;
        }
        return parse(date, DEFAULT_DATETIME_FORMAT);
    }

    public static LocalDateTime parse(String date, DateTimeFormatter formatter) {
        return LocalDateTime.parse(date, formatter);
    }

    public static String toString(Temporal temporal) {
        return toString(temporal, DEFAULT_DATETIME_FORMAT);
    }

    public static String toString(Temporal temporal, DateTimeFormatter formatter) {
        return formatter.format(temporal);
    }

}