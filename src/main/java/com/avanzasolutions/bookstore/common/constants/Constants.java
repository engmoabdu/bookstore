/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.constants;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author moabdu
 */

@Slf4j
public final class Constants {

    public static final String UTILITY_CLASS = "Utility class";

    public static class DateFormat {
        public static final String DATE_PATTERN = "yyyy-MM-dd";
        public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

        private DateFormat() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static final class Regex {

        public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$";

        private Regex() {
            log.info(UTILITY_CLASS);
        }
    }

    public static class Messages {
        public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
        public static final String NOT_NULL_ERROR_MESSAGE = "must not be null";
        public static final String NOT_BLANK_ERROR_MESSAGE = "must not be Blank";

        private Messages() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static final class Characters {
        public static final String SLASH = "/";
        public static final String PERCENTAGE = "%";
        public static final String COMMA = ",";
        public static final String COMMA_SPACE = ", ";
        public static final String SPACE = " ";
        public static final String EMPTY = "";
        public static final String LEFT_BRACKET = "(";
        public static final String RIGHT_BRACKET = ")";
        public static final String NEWLINE = "\n";

        private Characters() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static final class Authentication {

        public static final List<String> WHITE_LISTED_PATHS = List.of(
                "/abdu/**", "/v1/auth/register-user", "/v1/auth/register-admin",
                "/v1/books/search", "/v1/books/{bookId}"
        );

        private Authentication() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
