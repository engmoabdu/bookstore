/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.discount.enums;

import lombok.Getter;

@Getter
public enum BookPromotionTypes {

    FICTION("fiction"),
    COMIC("comic");

    private final String name;

    BookPromotionTypes(String name) {
        this.name = name;
    }
}
