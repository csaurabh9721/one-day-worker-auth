package com.onedayworker.auth.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {

    private static final ZoneId DEFAULT_ZONE = ZoneId.of("UTC");
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private DateTimeUtil() {
    }

    public static LocalDateTime now() {
        return ZonedDateTime.now(DEFAULT_ZONE).toLocalDateTime();
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(ISO_FORMATTER);
    }
}
