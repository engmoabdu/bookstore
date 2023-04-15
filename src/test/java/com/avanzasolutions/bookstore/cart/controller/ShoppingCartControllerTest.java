/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.controller;

import com.avanzasolutions.bookstore.book.dto.BookResponse;
import com.avanzasolutions.bookstore.cart.dto.cartitem.CartItemResponse;
import com.avanzasolutions.bookstore.cart.dto.shoppingcart.ShoppingCartResponse;
import com.avanzasolutions.bookstore.cart.service.ShoppingCartService;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.SuccessResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    private ShoppingCartResponse shoppingCartResponse;

    @BeforeEach
    void setup() {

        CartItemResponse itemResponse = CartItemResponse.builder()
                .id(1L)
                .book(BookResponse.builder().id(1L).author("Book Author").build())
                .quantity(2)
                .totalPrice(BigDecimal.valueOf(20.00))
                .build();

        shoppingCartResponse = ShoppingCartResponse.builder()
                .id(1L)
                .totalDiscountAmount(BigDecimal.valueOf(5.00))
                .subTotal(BigDecimal.valueOf(50.00))
                .tax(BigDecimal.valueOf(2.50))
                .totalPrice(BigDecimal.valueOf(47.50))
                .cartStatus("CHECKOUT")
                .active(true)
                .discountAmount(BigDecimal.valueOf(10.00))
                .discountedPrice(BigDecimal.valueOf(40.00))
                .cartItems(Collections.singletonList(itemResponse))
                .build();
    }

    @Test
    void givenNoInput_whenCreateCart_thenSuccess() {
        when(shoppingCartService.createCart()).thenReturn(shoppingCartResponse);

        ResponseEntity<SuccessResponse<ShoppingCartResponse>> response = shoppingCartController.createCart();

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).getData()).isEqualTo(shoppingCartResponse);
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("Cart created successfully");
    }

    @Test
    void givenNoInput_whenGetUserCart_thenSuccess() {
        when(shoppingCartService.getUserCart()).thenReturn(shoppingCartResponse);

        ResponseEntity<SuccessResponse<ShoppingCartResponse>> response = shoppingCartController.getUserCart();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).getData()).isEqualTo(shoppingCartResponse);
    }
}
