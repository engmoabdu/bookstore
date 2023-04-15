/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.discount;

import java.math.BigDecimal;

@FunctionalInterface
public interface Discount {
    BigDecimal calculateDiscount(BigDecimal price);
}
