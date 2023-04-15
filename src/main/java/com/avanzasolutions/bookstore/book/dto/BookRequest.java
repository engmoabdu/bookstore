/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.book.dto;

import com.avanzasolutions.bookstore.common.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for Book APIs")
public class BookRequest {

    @NotNull(message = "name " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "name " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The name of the book.", example = "The Great Gatsby")
    private String name;

    @Schema(description = "The description of the book.", example = "A novel by F. Scott Fitzgerald")
    private String description;

    @NotNull(message = "author " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "author " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The author of the book.", example = "F. Scott Fitzgerald")
    private String author;

    @NotNull(message = "type " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "type " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The type/classification of the book.", example = "Fiction")
    private String type;

    @NotNull(message = "price " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "price " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The price of the book.", example = "20.99")
    private Double price;

    @NotNull(message = "isbn " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "isbn " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The ISBN number of the book.", example = "978-3-16-148410-0")
    private String isbn;
}
