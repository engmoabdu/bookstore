/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.enums;

import lombok.Getter;

/**
 * @author moabdu
 */

@Getter
public enum GeneralErrorCode {

    GENERAL("General error"),
    CONNECTION_FAILED("Could not connect"),
    ITEM_NOT_FOUND("Item Not Found"),
    INVALID_ITEM("Item is not valid"),
    MALFORMED_JSON_REQUEST("Malformed JSON request"),
    ERROR_WRITING_JSON("Error writing JSON output."),
    VALIDATION_ERROR("Validation error"),
    SOMETHING_WENT_WRONG("Something went wrong."),
    REQUEST_PARAMETER_MISSING("Request parameter is missing"),
    HANDLER_NOT_FOUND("Handler not found"),
    SERVICE_TIMEOUT("Service timeout");

    final String details;

    GeneralErrorCode(String details) {
        this.details = details;
    }
}
