/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.dto.checkout;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(description = "The response object for a checkout operation.")
public record CheckoutResponse(
        @Schema(description = "The total price of the items in the cart.") @Getter @Setter BigDecimal totalPrice,
        @Schema(description = "The total discount applied to the items in the cart.") @Getter @Setter BigDecimal totalDiscountPrice,
        @Schema(description = "The total price after applying the discount to the items in the cart.") @Getter @Setter BigDecimal totalPriceAfterDiscount
) {
}
