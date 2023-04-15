/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.discount.impl;

import com.avanzasolutions.bookstore.cart.discount.Discount;
import com.avanzasolutions.bookstore.cart.discount.util.DiscountValueUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FictionDiscount implements Discount {

    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        return price.multiply(DiscountValueUtil.FICTION_DISCOUNT);
    }
}