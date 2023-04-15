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
public enum ErrorCodes {

    // General errors
    INTERNAL_SERVER_ERROR("ERR-001", "Something went wrong"),
    HANDLER_NOT_FOUND("ERR-002", "No handler found"),
    REQUEST_PARAMETER_MISSING("ERR-003", "Missing request parameter "),
    INVALID_ARGUMENTS("ERR-004", "Invalid method arguments "),
    CONSTRAINT_VIOLATION("ERR-005", "Validation error"),
    METHOD_NOT_SUPPORTED("ERR-006", "Method not supported"),
    ERROR_WRITING_JSON("ERR-007", "Error in writing JSON"),
    ERROR_READING_JSON("ERR-008", "Error in reading JSON"),
    BIND_EXCEPTION("ERR-009", "Binding Exception Error"),
    MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION("ERR-010", "this is over of the max size, Please check size and try again"),
    SQL_EXCEPTION("ERR-011", "there is an error with database validation"),
    DATA_INTEGRITY_VIOLATION_EXCEPTION("ERR-012", "Data Integrity Violation Exception"),
    CONSTRAINT_VIOLATION_EXCEPTION("ERR-013", "Constraint Violation Exception"),
    BAD_REQUEST("ERR-014", "Something went wrong"),
    ACCESS_DENIED("ERR-015", "Access denied."),
    CONFLICT("ERR-016", "Conflict"),
    NOT_FOUND("ERR-017", "Not found"),
    UNAUTHORIZED("ERR-018", "Unauthorized"),
    // For Service,

    NOT_FOUND_EXCEPTION("ENTITY-ERR-01", "Not found exception for this : "),
    NOT_FOUND_ERR("MSG-01", "This ID: %s Not exist."),
    NOT_FOUND_VALID_CART("MSG-017", "Not find a valid cart");

    final String errorCode;
    final String message;

    ErrorCodes(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
