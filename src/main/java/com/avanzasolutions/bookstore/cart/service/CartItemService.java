/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.service;

import com.avanzasolutions.bookstore.cart.dto.cartitem.CartItemRequest;
import com.avanzasolutions.bookstore.cart.dto.cartitem.CartItemResponse;

public interface CartItemService {

    String addBookToCart(CartItemRequest cartItemRequest);

    String removeBookFromCart(Long bookId, Integer quantity);

    CartItemResponse updateCartItemQuantity(Long cartItemId, Integer newQuantity);
}
