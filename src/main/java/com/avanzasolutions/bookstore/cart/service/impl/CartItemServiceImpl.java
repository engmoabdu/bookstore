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
import com.avanzasolutions.bookstore.book.dto.BookResponse;
import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.book.service.BookService;
import com.avanzasolutions.bookstore.cart.dto.cartitem.CartItemRequest;
import com.avanzasolutions.bookstore.cart.dto.cartitem.CartItemResponse;
import com.avanzasolutions.bookstore.cart.entity.CartItem;
import com.avanzasolutions.bookstore.cart.entity.ShoppingCart;
import com.avanzasolutions.bookstore.cart.repository.CartItemRepository;
import com.avanzasolutions.bookstore.cart.service.CartItemService;
import com.avanzasolutions.bookstore.cart.service.ShoppingCartService;
import com.avanzasolutions.bookstore.cart.util.CalculationUtilService;
import com.avanzasolutions.bookstore.common.enums.ErrorCodes;
import com.avanzasolutions.bookstore.common.enums.SuccessCodes;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;
    private final CalculationUtilService calculationUtilService;
    private final BookService bookService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public String addBookToCart(CartItemRequest cartItemRequest) {

        Book book = this.getBookById(cartItemRequest.getBookId());

        ShoppingCart shoppingCart = this.retrieveShoppingCartForCurrentLoginUser();

        Integer quantity = cartItemRequest.getQuantity();

        this.addItemToCart(book, shoppingCart, quantity);

        return SuccessCodes.ADD_TO_CART_SUCCESS.getMessage();
    }

    @Override
    public String removeBookFromCart(Long bookId, Integer quantity) {
        if (Objects.nonNull(quantity)) {
            Book book = this.getBookById(bookId);
            ShoppingCart shoppingCart = this.retrieveShoppingCartForCurrentLoginUser();
            this.removeBookFromCartImpl(book, shoppingCart, quantity);
            return SuccessCodes.DELETED_CART_ITEM_SUCCESS.getMessage();
        }
        return this.cartItemRepository.findByShoppingCartAndBook(this.retrieveShoppingCartForCurrentLoginUser(), this.getBookById(bookId))
                .map(cartItem -> {
                    this.cartItemRepository.delete(cartItem);
                    return String.format(SuccessCodes.DELETED_SUCCESS.getMessage(), cartItem.getId());
                }).orElseThrow(() -> {
                    log.info("this bookId id : {} does not exist in the cart", bookId);
                    return new CustomNotFoundException(ErrorCodes.NOT_FOUND_ERR.getErrorCode(), ErrorCodes.NOT_FOUND_VALID_CART.getMessage());
                });
    }

    @Override
    public CartItemResponse updateCartItemQuantity(Long cartItemId, Integer newQuantity) {
        CartItem cartItem = this.findById(cartItemId);
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
        return CartItemResponse.builder()
                .book(modelMapper.map(cartItem.getBook(), BookResponse.class))
                .quantity(cartItem.getQuantity())
                .build();
    }

    private void addItemToCart(Book book, ShoppingCart cart, int quantity) {
        // check if book already exists in the cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // update quantity of existing item
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            calculationUtilService.updateCartItemPrice(cartItem);
            cartItemRepository.save(cartItem);
        } else {
            // create new cart item
            CartItem newItem = CartItem.builder()
                    .book(book)
                    .shoppingCart(cart)
                    .quantity(quantity)
                    .build();
            cart.getItems().add(newItem);
            calculationUtilService.updateCartItemPrice(newItem);
            cartItemRepository.save(newItem);
        }
        calculationUtilService.updateCartPrices(cart);
        shoppingCartService.update(cart);
    }

    // remove book from shopping cart
    private void removeBookFromCartImpl(Book book, ShoppingCart shoppingCart, int quantity) {
        Optional<CartItem> existingItem = shoppingCart.getItems().stream()
                .filter(item -> item.getBook().equals(book))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            if (quantity >= cartItem.getQuantity()) {
                // remove entire cart item
                shoppingCart.getItems().remove(cartItem);
            } else {
                // update quantity and total price of cart item
                cartItem.setQuantity(cartItem.getQuantity() - quantity);
                cartItem.setTotalPrice(BigDecimal.valueOf(cartItem.getBook().getPrice())
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            }
        }
        // update total price of shopping cart
        calculationUtilService.updateCartPrices(shoppingCart);
        shoppingCartService.update(shoppingCart);
    }

    private User retrieveCurrentLoginUser() {
        User user = userService.retrieveCurrentLoginUser();
        log.info("username : {}", user.getUsername());
        return user;
    }

    private ShoppingCart retrieveShoppingCartForCurrentLoginUser() {
        ShoppingCart shoppingCart = shoppingCartService.retrieveActiveCartForUser(this.retrieveCurrentLoginUser());
        log.info("shoppingCart id is : {}", shoppingCart.getId());
        return shoppingCart;
    }

    private Book getBookById(Long id) {
        Book book = bookService.findById(id);
        log.info("book id is : {}", id);
        return book;
    }

    private CartItem findById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomNotFoundException(
                        ErrorCodes.NOT_FOUND_ERR.getErrorCode(),
                        String.format(ErrorCodes.NOT_FOUND_ERR.getMessage(), cartItemId)));
    }
}
