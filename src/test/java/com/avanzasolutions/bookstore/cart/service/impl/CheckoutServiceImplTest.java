/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.service.impl;

import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.cart.discount.DiscountFactory;
import com.avanzasolutions.bookstore.cart.discount.impl.ComicDiscount;
import com.avanzasolutions.bookstore.cart.discount.impl.FictionDiscount;
import com.avanzasolutions.bookstore.cart.dto.checkout.CheckoutResponse;
import com.avanzasolutions.bookstore.cart.entity.CartItem;
import com.avanzasolutions.bookstore.cart.entity.ShoppingCart;
import com.avanzasolutions.bookstore.cart.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    @Mock
    private DiscountFactory discountFactory;

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private ShoppingCart shoppingCart;

    @BeforeEach
    void setup() {
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

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);

        shoppingCart.setItems(items);

        ShoppingCart cart = new ShoppingCart();
        cart.setItems(items);
    }

    @Test
    void testCheckoutWithNoPromotionCode() {
        shoppingCart.setActive(true);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(50));

        when(shoppingCartService.getUserCartEntity()).thenReturn(shoppingCart);

        Mockito.when(discountFactory.getDiscount("fiction")).thenReturn(new FictionDiscount());
        Mockito.when(discountFactory.getDiscount("comic")).thenReturn(new ComicDiscount());

        CheckoutResponse response = checkoutService.checkout(null);

        assertThat(BigDecimal.valueOf(36.97)).isEqualTo(response.getTotalPriceAfterDiscount());
    }

    @Test
    void testCheckoutWithPromotionCode() {
        shoppingCart.setActive(true);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(50));

        when(shoppingCartService.getUserCartEntity()).thenReturn(shoppingCart);

        Mockito.when(discountFactory.getDiscount("fiction")).thenReturn(new FictionDiscount());
        Mockito.when(discountFactory.getDiscount("comic")).thenReturn(new ComicDiscount());

        CheckoutResponse response = checkoutService.checkout("PROMO");

        assertThat(BigDecimal.valueOf(50)).isEqualTo(response.getTotalPrice());
        assertThat(BigDecimal.valueOf(0)).isEqualTo(response.getTotalDiscountPrice());
        assertThat(BigDecimal.valueOf(36.97)).isEqualTo(response.getTotalPriceAfterDiscount());
    }

    @Test
    void testCheckoutWithBlankPromotionCode() {
        shoppingCart.setActive(true);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(50));

        when(shoppingCartService.getUserCartEntity()).thenReturn(shoppingCart);

        Mockito.when(discountFactory.getDiscount("fiction")).thenReturn(new FictionDiscount());
        Mockito.when(discountFactory.getDiscount("comic")).thenReturn(new ComicDiscount());

        CheckoutResponse response = checkoutService.checkout("");

        assertThat(BigDecimal.valueOf(50)).isEqualTo(response.getTotalPrice());
        assertThat(BigDecimal.valueOf(0)).isEqualTo(response.getTotalDiscountPrice());
        assertThat(BigDecimal.valueOf(36.97)).isEqualTo(response.getTotalPriceAfterDiscount());
    }
}
