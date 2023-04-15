/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.service.impl;

import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.cart.dto.checkout.CheckoutResponse;
import com.avanzasolutions.bookstore.cart.entity.ShoppingCart;
import com.avanzasolutions.bookstore.cart.discount.Discount;
import com.avanzasolutions.bookstore.cart.discount.DiscountFactory;
import com.avanzasolutions.bookstore.cart.service.CheckoutService;
import com.avanzasolutions.bookstore.cart.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final DiscountFactory discountFactory;
    private final ShoppingCartService shoppingCartService;

    @Override
    public CheckoutResponse checkout(String promotionCode) {

        ShoppingCart shoppingCart = shoppingCartService.getUserCartEntity();

        BigDecimal totalPrice = shoppingCart.getTotalPrice();

        AtomicReference<BigDecimal> itemDiscount = new AtomicReference<>(BigDecimal.ZERO);

        BigDecimal totalPriceAfterDiscount = shoppingCart.getItems().stream()
                .map(item -> {
                    Book book = item.getBook();
                    BigDecimal itemPrice = item.getTotalPrice();

                    // Apply discount based on book type/classification
                    Discount discount = discountFactory.getDiscount(book.getType());

                    itemDiscount.set(discount.calculateDiscount(itemPrice));

                    return BigDecimal.valueOf(item.getQuantity() * book.getPrice()).subtract(itemDiscount.get());
                }).reduce(BigDecimal.ZERO, BigDecimal::add);

        // apply promotion code if available
        if (Objects.nonNull(promotionCode) && !promotionCode.isBlank()) {
            // apply discount based on promotion code
            // TODO: implement code to apply promotion code
            log.info("The user used the promotion code");
        }

        return new CheckoutResponse(totalPrice, itemDiscount.get(), totalPriceAfterDiscount);
    }
}
