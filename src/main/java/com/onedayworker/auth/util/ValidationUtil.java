package com.onedayworker.auth.util;

import java.util.Objects;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNull(Object value) {
        return Objects.isNull(value);
    }
}
