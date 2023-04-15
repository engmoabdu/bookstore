/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.dto.cartitem;

import com.avanzasolutions.bookstore.book.dto.BookResponse;
import com.avanzasolutions.bookstore.common.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response object for a single item in the cart")
public class CartItemResponse extends BaseResponse<Long> {

    @Schema(description = "The book associated with this cart item")
    private BookResponse book;

    @Schema(description = "The quantity of the book in this cart item")
    private Integer quantity;

    @Schema(description = "The total price of this cart item")
    private BigDecimal totalPrice;
}

