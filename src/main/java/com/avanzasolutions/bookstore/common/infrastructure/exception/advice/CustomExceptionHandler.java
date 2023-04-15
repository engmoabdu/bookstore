/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.infrastructure.exception.advice;

import com.avanzasolutions.bookstore.common.enums.ErrorCodes;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.AbstractBaseResponse;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.ErrorResponse;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomDataException;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomServiceException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author moabdu
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({CustomNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<AbstractBaseResponse> handle(CustomNotFoundException exception) {
        return this.createGlobalException(exception.getMessage(), exception.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<AbstractBaseResponse> handle(CustomServiceException exception) {
        return this.createGlobalException(exception.getMessage(), exception.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomDataException.class)
    public ResponseEntity<AbstractBaseResponse> handle(CustomDataException exception) {
        return this.createGlobalException(exception.getMessage(), exception.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AbstractBaseResponse> handle(AccessDeniedException accessDeniedException) {
        return unauthorizedResponse();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<AbstractBaseResponse> handle(ConstraintViolationException exception) {
        return this.createGlobalException(
                exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining()),
                ErrorCodes.BAD_REQUEST.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<AbstractBaseResponse> handle(MissingRequestHeaderException exception, HttpStatus status) {
        return this.createGlobalException(
                exception.getHeaderName() + " parameter is missing", ErrorCodes.REQUEST_PARAMETER_MISSING.getErrorCode(), status);
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<AbstractBaseResponse> handle(MaxUploadSizeExceededException maxUploadSizeExceededException) {
        return this.createGlobalException(maxUploadSizeExceededException.getMessage(),
                ErrorCodes.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION.getErrorCode(), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<AbstractBaseResponse> handle(SQLException exception) {
        return this.createGlobalException(exception.getMessage(),
                ErrorCodes.SQL_EXCEPTION.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<AbstractBaseResponse> handle(NoSuchElementException exception) {
        return this.createGlobalException(
                exception.getMessage(), ErrorCodes.NOT_FOUND.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * @param exception DuplicateKeyException
     * @return BaseResponse base response
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<AbstractBaseResponse> handle(DuplicateKeyException exception) {
        return this.createGlobalException(
                exception.getMessage(), ErrorCodes.CONFLICT.getErrorCode(), HttpStatus.CONFLICT);
    }

    /**
     * @param exception IllegalArgumentException
     * @return BaseResponse base response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AbstractBaseResponse> handle(IllegalArgumentException exception) {
        return this.createGlobalException(
                exception.getMessage(), ErrorCodes.BAD_REQUEST.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param exception ValidationException
     * @return BaseResponse base response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<AbstractBaseResponse> handle(ValidationException exception) {
        return this.createGlobalException(
                exception.getMessage(), ErrorCodes.BAD_REQUEST.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param exception InvalidFormatException
     * @return BaseResponse base response
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<AbstractBaseResponse> handle(InvalidFormatException exception) {
        log.debug(exception.toString(), exception);
        log.error("InvalidFormatException message {}, {}", exception.getMessage(), exception.getStackTrace());
        List<String> errorsList = new ArrayList<>();
        Class<?> type = exception.getTargetType();
        if (type != null) {
            return new ResponseEntity<>(
                    new DetailedErrorResponseAbstract<>("Invalid request", HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                            exception.getMessage(), Collections.singletonList(
                            "The parameter of '" + type.getSimpleName() + " must have a value among:"
                                    + StringUtils.join(type.getEnumConstants(), ", "))), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        exception.getPath().forEach(reference -> errorsList.add("Value of '" + reference.getFieldName() + " not a valid representation"));
        return new ResponseEntity<>(
                new DetailedErrorResponseAbstract<>("Invalid request", HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        exception.getMessage(), errorsList), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param exception PersistenceException
     * @return BaseResponse
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<AbstractBaseResponse> handle(PersistenceException exception) {
        log.error("Error message :{}, {}", exception.getMessage(), exception.getStackTrace());
        if (exception.getCause() instanceof ConstraintViolationException) {
            return this.createGlobalException(exception.getCause().getCause().getMessage(),
                    ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return handleException(exception);
        }
    }

    /**
     * @param exception DataIntegrityViolationException
     * @return BaseResponse
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<AbstractBaseResponse> handle(DataIntegrityViolationException exception) {
        log.error("DataIntegrityViolationException message ----> {}, {}", exception.getMessage(), exception.getStackTrace());
        return this.createGlobalException(exception.getCause().getCause().getMessage(),
                ErrorCodes.DATA_INTEGRITY_VIOLATION_EXCEPTION.getErrorCode(), HttpStatus.CONFLICT);
    }

    /**
     * @param exception RuntimeException
     * @return BaseResponse
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AbstractBaseResponse> handle(RuntimeException exception) {
        log.debug(exception.toString(), exception);
        log.error("RuntimeException Error message --> {}, {}", exception.getMessage(), exception.getStackTrace());
        return this.createGlobalException(
                exception.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - Internal server error
     *
     * @param exception Exception
     * @return BaseResponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AbstractBaseResponse> handleException(Exception exception) {
        log.debug(exception.toString(), exception);
        log.error("Exception message -->> {}, {}", exception.getMessage(), exception.getStackTrace());
        return this.createGlobalException(
                exception.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<AbstractBaseResponse> createGlobalException(String errorMessage, String errorCode, HttpStatus httpStatus) {
        return new ResponseEntity<>(ErrorResponse.builder().message(errorMessage).errorCode(errorCode).build(), httpStatus);
    }

    private ResponseEntity<AbstractBaseResponse> unauthorizedResponse() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder().message(ErrorCodes.ACCESS_DENIED.getMessage())
                .errorCode(ErrorCodes.ACCESS_DENIED.getErrorCode()).build());
    }
}
