/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.infrastructure.exception.advice;

import com.avanzasolutions.bookstore.common.infrastructure.dto.response.AbstractBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author moabdu
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DetailedErrorResponseAbstract<H extends Serializable, D> extends AbstractBaseResponse {

    private H errorHeader;
    private D errorDetails;

    @Override
    public Boolean isSuccess() {
        return Boolean.FALSE;
    }

    public DetailedErrorResponseAbstract(String message, String responseCode, H errorHeader, D errorDetails) {
        super(Boolean.FALSE, message, responseCode);
        this.errorHeader = errorHeader;
        this.errorDetails = errorDetails;
    }
}

