/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.service;

import com.avanzasolutions.bookstore.auth.entity.User;
import com.avanzasolutions.bookstore.cart.dto.shoppingcart.ShoppingCartResponse;
import com.avanzasolutions.bookstore.cart.entity.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCartResponse createCart();

    ShoppingCartResponse getUserCart();

    ShoppingCart retrieveActiveCartForUser(User user);

    ShoppingCart getUserCartEntity();

    void update(ShoppingCart shoppingCart);
}
