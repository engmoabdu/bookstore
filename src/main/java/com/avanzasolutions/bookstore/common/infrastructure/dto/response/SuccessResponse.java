/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author moabdu
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Success Response")
public class SuccessResponse<T> extends AbstractBaseResponse {

    @Schema(description = "The success data")
    private T data;

    @Override
    public Boolean isSuccess() {
        return Boolean.TRUE;
    }
}

