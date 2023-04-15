/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.discount.util;

import com.avanzasolutions.bookstore.common.constants.Constants;

import java.math.BigDecimal;

public final class DiscountValueUtil {
    public static final BigDecimal COMIC_DISCOUNT = BigDecimal.ZERO; // 0% discount
    public static final BigDecimal FICTION_DISCOUNT = BigDecimal.valueOf(0.1); // 10% discount

    private DiscountValueUtil() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }
}
