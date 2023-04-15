/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.repository;

import com.avanzasolutions.bookstore.auth.entity.User;
import com.avanzasolutions.bookstore.cart.entity.ShoppingCart;
import com.avanzasolutions.bookstore.common.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends BaseRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserAndActiveIsTrue(User user);
}
