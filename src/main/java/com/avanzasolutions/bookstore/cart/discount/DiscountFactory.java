/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.discount;

import com.avanzasolutions.bookstore.cart.discount.impl.ComicDiscount;
import com.avanzasolutions.bookstore.cart.discount.impl.NoDiscount;
import com.avanzasolutions.bookstore.cart.discount.impl.FictionDiscount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountFactory {

    private final FictionDiscount fictionDiscount;
    private final ComicDiscount comicDiscount;
    private final NoDiscount noDiscount;

    public Discount getDiscount(String bookType) {
        return switch (bookType) {
            case "fiction" -> fictionDiscount;
            case "comic" -> comicDiscount;
            default -> noDiscount;
        };
    }
}
