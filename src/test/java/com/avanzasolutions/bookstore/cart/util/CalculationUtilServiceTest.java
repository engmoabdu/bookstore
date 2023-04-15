/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.util;

import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.cart.discount.DiscountFactory;
import com.avanzasolutions.bookstore.cart.discount.impl.ComicDiscount;
import com.avanzasolutions.bookstore.cart.discount.impl.FictionDiscount;
import com.avanzasolutions.bookstore.cart.entity.CartItem;
import com.avanzasolutions.bookstore.cart.entity.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalculationUtilServiceTest {

    @InjectMocks
    private CalculationUtilService calculationUtilService;
    @Mock
    private DiscountFactory discountFactory;

    @Test
    void testUpdateCartItemPrice() {
        Book book = Book.builder()
                .price(10.0)
                .build();
        CartItem item = CartItem.builder()
                .book(book)
                .quantity(2)
                .build();

        calculationUtilService.updateCartItemPrice(item);

        assertThat(item.getTotalPrice()).isEqualTo(BigDecimal.valueOf(20.0));
    }

    @Test
    void updateCartPrices_shouldCalculateTotalPriceAndDiscountAmount() {
        Book book1 = Book.builder()
                .id(1L)
                .name("The Great Gatsby")
                .description("A novel by F. Scott Fitzgerald")
                .author("F. Scott Fitzgerald")
                .type("fiction")
                .price(12.99)
                .isbn("978-3-16-148410-0")
                .build();
        Book book2 = Book.builder()
                .id(2L)
                .name("The Great Gatsby")
                .description("A novel by F. Scott Fitzgerald")
                .author("F. Scott Fitzgerald")
                .type("comic")
                .price(12.99)
                .isbn("978-3-16-148410-0")
                .build();

        CartItem item1 = CartItem.builder()
                .book(book1)
                .quantity(2)
                .totalPrice(BigDecimal.valueOf(20))
                .build();
        CartItem item2 = CartItem.builder()
                .book(book2)
                .quantity(1)
                .totalPrice(BigDecimal.valueOf(20))
                .build();

        List<CartItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);

        shoppingCart.setItems(items);

        ShoppingCart cart = new ShoppingCart();
        cart.setItems(items);

        Mockito.when(discountFactory.getDiscount("fiction")).thenReturn(new FictionDiscount());
        Mockito.when(discountFactory.getDiscount("comic")).thenReturn(new ComicDiscount());

        calculationUtilService.updateCartPrices(cart);

        BigDecimal expectedTotalPrice = new BigDecimal("38.97");
        BigDecimal expectedDiscountAmount = new BigDecimal("2.0");
        BigDecimal expectedDiscountedPrice = new BigDecimal("36.97");

        assertThat(cart.getTotalPrice()).isEqualTo(expectedTotalPrice);
        assertThat(cart.getDiscountAmount()).isEqualTo(expectedDiscountAmount);
        assertThat(cart.getDiscountedPrice()).isEqualTo(expectedDiscountedPrice);
    }
}
