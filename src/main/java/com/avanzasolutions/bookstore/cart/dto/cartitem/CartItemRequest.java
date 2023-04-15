/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.dto.cartitem;

import com.avanzasolutions.bookstore.common.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Request object for CartItem APIs")
public class CartItemRequest {

    @NotNull(message = "bookId " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "bookId " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The ID of the book being added to the cart.")
    private Long bookId;

    @NotNull(message = "quantity " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "quantity " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The quantity of the book being added to the cart.")
    private Integer quantity;
}