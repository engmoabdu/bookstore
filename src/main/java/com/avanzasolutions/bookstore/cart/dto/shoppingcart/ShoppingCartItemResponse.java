/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.dto.shoppingcart;

import com.avanzasolutions.bookstore.book.dto.BookResponse;
import com.avanzasolutions.bookstore.common.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Shopping cart item response")
public class ShoppingCartItemResponse extends BaseResponse<Long> {

    @Schema(description = "Book response")
    private BookResponse book;

    @Schema(description = "Quantity of the book in the shopping cart")
    private Integer quantity;
}

