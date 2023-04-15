/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.dto.shoppingcart;

import com.avanzasolutions.bookstore.cart.dto.cartitem.CartItemResponse;
import com.avanzasolutions.bookstore.common.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Shopping cart details")
public class ShoppingCartResponse extends BaseResponse<Long> {

    @Schema(description = "Total discount amount")
    private BigDecimal totalDiscountAmount;

    @Schema(description = "Sub total price of all cart items")
    private BigDecimal subTotal;

    @Schema(description = "Tax amount")
    private BigDecimal tax;

    @Schema(description = "Total price of all cart items")
    private BigDecimal totalPrice;

    @Schema(description = "Status of the cart")
    private String cartStatus;

    @Schema(description = "Boolean flag indicating whether cart is active or not")
    private Boolean active;

    @Schema(description = "Discount amount")
    private BigDecimal discountAmount;

    @Schema(description = "Discounted price")
    private BigDecimal discountedPrice;

    @Schema(description = "List of cart items")
    private List<CartItemResponse> cartItems;
}

