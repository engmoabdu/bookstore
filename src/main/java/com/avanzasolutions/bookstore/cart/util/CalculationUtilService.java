/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.util;

import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.cart.entity.CartItem;
import com.avanzasolutions.bookstore.cart.entity.ShoppingCart;
import com.avanzasolutions.bookstore.cart.discount.Discount;
import com.avanzasolutions.bookstore.cart.discount.DiscountFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CalculationUtilService {

    private final DiscountFactory discountFactory;

    public void updateCartItemPrice(CartItem item) {
        BigDecimal itemPrice = BigDecimal.valueOf(item.getBook().getPrice())
                .multiply(BigDecimal.valueOf(item.getQuantity()));
        item.setTotalPrice(itemPrice);
    }

    public void updateCartPrices(ShoppingCart cart) {
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> BigDecimal.valueOf(item.getBook().getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);

        BigDecimal discountAmount = cart.getItems().stream()
                .map(item -> {
                    Book book = item.getBook();
                    // Apply discount based on book type/classification
                    Discount discount = discountFactory.getDiscount(book.getType());
                    return discount.calculateDiscount(item.getTotalPrice());
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setDiscountAmount(discountAmount);
        cart.setDiscountedPrice(totalPrice.subtract(discountAmount));
    }
}

