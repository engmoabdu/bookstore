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
import com.avanzasolutions.bookstore.cart.service.ShoppingCartService;
import com.avanzasolutions.bookstore.common.enums.ErrorCodes;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public ShoppingCartResponse createCart() {
        return modelMapper.map(shoppingCartRepository.save(ShoppingCart.builder()
                .cartStatus(CartStatus.DRAFT)
                .user(retrieveCurrentLoginUser()).active(Boolean.TRUE).build()), ShoppingCartResponse.class);
    }

    @Override
    public ShoppingCartResponse getUserCart() {
        return modelMapper.map(retrieveActiveCartForUser(retrieveCurrentLoginUser()), ShoppingCartResponse.class);
    }

    @Override
    public ShoppingCart getUserCartEntity() {
        return retrieveActiveCartForUser(retrieveCurrentLoginUser());
    }

    @Override
    public ShoppingCart retrieveActiveCartForUser(User user) {
        return this.shoppingCartRepository.findByUserAndActiveIsTrue(user).orElseThrow(() -> {
            log.info("This username : {} not has a valid cart.", user.getUsername());
            return new CustomNotFoundException(ErrorCodes.NOT_FOUND_VALID_CART.getMessage(), ErrorCodes.NOT_FOUND_VALID_CART.getErrorCode());
        });
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    private User retrieveCurrentLoginUser() {
        User user = userService.retrieveCurrentLoginUser();
        log.info("username : {}", user.getUsername());
        return user;
    }
}
