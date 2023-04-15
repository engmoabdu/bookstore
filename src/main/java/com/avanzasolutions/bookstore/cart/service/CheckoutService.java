/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.service;

import com.avanzasolutions.bookstore.cart.dto.checkout.CheckoutResponse;

public interface CheckoutService {

    CheckoutResponse checkout(String promotionCode);
}
