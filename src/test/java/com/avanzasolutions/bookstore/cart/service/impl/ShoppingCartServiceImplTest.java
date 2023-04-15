/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.service.impl;

import com.avanzasolutions.bookstore.auth.entity.User;
import com.avanzasolutions.bookstore.auth.service.UserService;
import com.avanzasolutions.bookstore.cart.dto.shoppingcart.ShoppingCartResponse;
import com.avanzasolutions.bookstore.cart.entity.ShoppingCart;
import com.avanzasolutions.bookstore.cart.enums.CartStatus;
import com.avanzasolutions.bookstore.cart.repository.ShoppingCartRepository;
import com.avanzasolutions.bookstore.common.enums.ErrorCodes;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Test
    void testCreateCart() {
        User user = new User();
        user.setUsername("testuser");
        when(userService.retrieveCurrentLoginUser()).thenReturn(user);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartStatus(CartStatus.DRAFT);
        shoppingCart.setUser(user);
        shoppingCart.setActive(true);

        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        ShoppingCartResponse expectedResponse = new ShoppingCartResponse();
        expectedResponse.setCartStatus(CartStatus.DRAFT.getDisplayName());
        expectedResponse.setActive(true);

        when(modelMapper.map(shoppingCart, ShoppingCartResponse.class)).thenReturn(expectedResponse);

        ShoppingCartResponse actualResponse = shoppingCartService.createCart();

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(shoppingCartRepository).save(any(ShoppingCart.class));
        verify(userService).retrieveCurrentLoginUser();
        verify(modelMapper).map(shoppingCart, ShoppingCartResponse.class);
    }

    @Test
    void testGetUserCart() {
        User user = new User();
        user.setUsername("testuser");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartStatus(CartStatus.DRAFT);
        shoppingCart.setUser(user);
        shoppingCart.setActive(true);
        ShoppingCartResponse expectedResponse = new ShoppingCartResponse();
        expectedResponse.setCartStatus(CartStatus.DRAFT.getDisplayName());
        expectedResponse.setActive(true);

        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(shoppingCartRepository.findByUserAndActiveIsTrue(user)).thenReturn(Optional.of(shoppingCart));
        when(modelMapper.map(shoppingCart, ShoppingCartResponse.class)).thenReturn(expectedResponse);

        ShoppingCartResponse actualResponse = shoppingCartService.getUserCart();

        org.assertj.core.api.Assertions.assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void testGetUserCartEntity() {
        User user = new User();
        user.setUsername("testuser");
        ShoppingCart expectedShoppingCart = new ShoppingCart();
        expectedShoppingCart.setCartStatus(CartStatus.DRAFT);
        expectedShoppingCart.setUser(user);
        expectedShoppingCart.setActive(true);

        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(shoppingCartRepository.findByUserAndActiveIsTrue(user)).thenReturn(Optional.of(expectedShoppingCart));

        ShoppingCart actualShoppingCart = shoppingCartService.getUserCartEntity();

        org.assertj.core.api.Assertions.assertThat(actualShoppingCart).isEqualTo(expectedShoppingCart);
    }

    @Test
    void testRetrieveActiveCartForUserSuccess() {
        User user = new User();
        user.setUsername("testuser");
        ShoppingCart expectedShoppingCart = new ShoppingCart();
        expectedShoppingCart.setCartStatus(CartStatus.DRAFT);
        expectedShoppingCart.setUser(user);
        expectedShoppingCart.setActive(true);

        when(shoppingCartRepository.findByUserAndActiveIsTrue(user)).thenReturn(Optional.of(expectedShoppingCart));

        ShoppingCart actualShoppingCart = shoppingCartService.retrieveActiveCartForUser(user);

        org.assertj.core.api.Assertions.assertThat(expectedShoppingCart).isEqualTo(actualShoppingCart);
    }

    @Test
    void testRetrieveActiveCartForUserFail() {
        User user = new User();
        user.setUsername("testuser");

        when(shoppingCartRepository.findByUserAndActiveIsTrue(user)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class,
                () -> shoppingCartService.retrieveActiveCartForUser(user));
        org.assertj.core.api.Assertions.assertThat(ErrorCodes.NOT_FOUND_VALID_CART.getErrorCode()).isEqualTo(exception.getErrorCode());
        org.assertj.core.api.Assertions.assertThat(ErrorCodes.NOT_FOUND_VALID_CART.getMessage()).isEqualTo(exception.getMessage());
    }

    @Test
    void testUpdate() {
        ShoppingCart expectedShoppingCart = new ShoppingCart();
        expectedShoppingCart.setCartStatus(CartStatus.DRAFT);
        expectedShoppingCart.setUser(new User());
        expectedShoppingCart.setActive(true);

        shoppingCartService.update(expectedShoppingCart);

        verify(shoppingCartRepository).save(expectedShoppingCart);
    }
}
