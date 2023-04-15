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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class ComicDiscount implements Discount {

    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        log.info("This promotion is applied to this price {} with 0% discount", price);
        return DiscountValueUtil.COMIC_DISCOUNT;
    }
}