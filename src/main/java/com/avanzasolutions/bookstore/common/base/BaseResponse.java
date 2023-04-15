/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The base response object for all API responses.")
public class BaseResponse<I extends Serializable> {

    @Schema(description = "The ID of the entity.")
    private I id;

    @Schema(description = "The username of the user who created the entity.")
    private String createdBy;

    @Schema(description = "The creation date of the entity.")
    private String creationDate;

    @Schema(description = "The username of the user who last modified the entity.")
    private String lastModifiedBy;

    @Schema(description = "The last modified date of the entity.")
    private String lastModifiedDate;
}
