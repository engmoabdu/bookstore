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
import com.avanzasolutions.bookstore.cart.service.ShoppingCartService;
import com.avanzasolutions.bookstore.cart.util.CalculationUtilService;
import com.avanzasolutions.bookstore.common.enums.SuccessCodes;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CalculationUtilService calculationUtilService;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    private User user;
    private ShoppingCart shoppingCart;
    private Book book;
    private CartItem cartItem;
    private CartItemRequest cartItemRequest;
    private BookResponse bookResponse;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1);
        user.setUsername("testUser");

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setItems(new ArrayList<>());

        book = Book.builder()
                .id(1L)
                .name("The Great Gatsby")
                .description("A novel by F. Scott Fitzgerald")
                .author("F. Scott Fitzgerald")
                .type("fiction")
                .price(12.99)
                .isbn("978-3-16-148410-0")
                .build();

        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(2);

        cartItemRequest = new CartItemRequest();
        cartItemRequest.setBookId(1L);
        cartItemRequest.setQuantity(4);

        bookResponse = BookResponse.builder()
                .id(123L)
                .name("The Great Gatsby")
                .description("A novel by F. Scott Fitzgerald")
                .author("F. Scott Fitzgerald")
                .type("Fiction")
                .price(12.99)
                .isbn("978-3-16-148410-0")
                .build();

    }

    @Test
    @DisplayName("Add book to cart - success")
    void testAddBookToCartSuccess() {
        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(shoppingCartService.retrieveActiveCartForUser(any())).thenReturn(shoppingCart);
        when(bookService.findById(anyLong())).thenReturn(book);
        doAnswer(invocation -> {
            CartItem newItem = invocation.getArgument(0);
            shoppingCart.setItems(Collections.singletonList(newItem));
            return null;
        }).when(cartItemRepository).save(any());
        doNothing().when(calculationUtilService).updateCartPrices(any());
        String actualResult = cartItemService.addBookToCart(cartItemRequest);
        assertThat(actualResult).isEqualTo(SuccessCodes.ADD_TO_CART_SUCCESS.getMessage());
        CartItem newItem = shoppingCart.getItems().get(0);
        assertThat(newItem.getBook().getId()).isEqualTo(book.getId());
        assertThat(newItem.getShoppingCart()).isEqualTo(shoppingCart);
        assertThat(newItem.getQuantity()).isEqualTo(cartItemRequest.getQuantity());
        verify(calculationUtilService).updateCartItemPrice(any());
        verify(cartItemRepository).save(any());
        verify(calculationUtilService).updateCartPrices(any());
        verify(shoppingCartService).update(any());
    }

    @Test
    void testAddItemToCartExistingItemPresent() {
        CartItem existingItem = CartItem.builder().book(book).shoppingCart(shoppingCart).quantity(6).build();
        existingItem.setId(1L);
        shoppingCart.getItems().add(existingItem);
        when(bookService.findById(book.getId())).thenReturn(book);
        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(shoppingCartService.retrieveActiveCartForUser(any())).thenReturn(shoppingCart);
        when(cartItemRepository.save(any())).thenReturn(existingItem);

        String result = cartItemService.addBookToCart(cartItemRequest);

        assertThat(result).isEqualTo(SuccessCodes.ADD_TO_CART_SUCCESS.getMessage());
        assertThat(shoppingCart.getItems()).hasSize(1);
        CartItem newItem = shoppingCart.getItems().get(0);
        assertThat(newItem.getId()).isEqualTo(existingItem.getId());
        assertThat(newItem.getBook()).isEqualTo(existingItem.getBook());
        assertThat(newItem.getQuantity()).isEqualTo(existingItem.getQuantity());
        verify(calculationUtilService).updateCartItemPrice(newItem);
        verify(calculationUtilService).updateCartPrices(shoppingCart);
        verify(shoppingCartService).update(shoppingCart);
        verify(cartItemRepository).save(newItem);
    }

    @Test
    void testRemoveBookFromCartRemoveEntireItem() {
        shoppingCart.getItems().add(cartItem);
        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(shoppingCartService.retrieveActiveCartForUser(any())).thenReturn(shoppingCart);
        String result = cartItemService.removeBookFromCart(1L, 2);
        assertThat(result).isEqualTo(SuccessCodes.DELETED_CART_ITEM_SUCCESS.getMessage());
        assertThat(shoppingCart.getItems()).hasSize(1);
    }

    @Test
    void testRemoveBookFromCartExistingItemPresent() {
        Long bookId = 1L;
        Integer quantity = 2;
        Book book = Book.builder()
                .id(bookId)
                .price(10.0)
                .build();
        User user = User.builder()
                .username("user")
                .accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).enabled(true).build();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .id(1L)
                .user(user)
                .items(new ArrayList<>())
                .build();
        CartItem cartItem = CartItem.builder()
                .id(1L)
                .book(book)
                .shoppingCart(shoppingCart)
                .quantity(3)
                .totalPrice(BigDecimal.valueOf(30))
                .build();
        shoppingCart.getItems().add(cartItem);

        when(bookService.findById(bookId)).thenReturn(book);
        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(shoppingCartService.retrieveActiveCartForUser(user)).thenReturn(shoppingCart);

        String result = cartItemService.removeBookFromCart(bookId, quantity);

        assertThat(result).isEqualTo(SuccessCodes.DELETED_CART_ITEM_SUCCESS.getMessage());
        assertThat(shoppingCart.getItems()).hasSize(1);
        assertThat(shoppingCart.getItems().get(0).getId().longValue()).isEqualTo(1L);
        assertThat(shoppingCart.getItems().get(0).getBook()).isEqualTo(book);
        assertThat(shoppingCart.getItems().get(0).getQuantity().intValue()).isEqualTo(1);
        assertThat(shoppingCart.getItems().get(0).getTotalPrice()).isEqualTo(BigDecimal.valueOf(10.0));
    }

    @Test
    void testRemoveBookFromCartNullQuantity() {
        Long bookId = 1L;
        shoppingCart.getItems().add(cartItem);
        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(bookService.findById(bookId)).thenReturn(book);
        when(shoppingCartService.retrieveActiveCartForUser(any(User.class))).thenReturn(shoppingCart);
        when(cartItemRepository.findByShoppingCartAndBook(shoppingCart, book)).thenReturn(Optional.of(cartItem));

        String result = cartItemService.removeBookFromCart(bookId, null);

        assertThat(result).isEqualTo(String.format(SuccessCodes.DELETED_SUCCESS.getMessage(), bookId));
    }

    @Test
    void testRemoveBookFromCartNullQuantityCartItemNotFound() {
        when(bookService.findById(1L)).thenReturn(book);
        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(shoppingCartService.retrieveActiveCartForUser(any(User.class))).thenReturn(shoppingCart);
        when(cartItemRepository.findByShoppingCartAndBook(shoppingCart, book)).thenReturn(Optional.empty());
        assertThatCode(() -> cartItemService.removeBookFromCart(1L, null)).isInstanceOf(CustomNotFoundException.class);
        verify(cartItemRepository).findByShoppingCartAndBook(shoppingCart, book);
        verify(cartItemRepository, never()).delete(any(CartItem.class));
    }

    @Test
    void updateCartItemQuantity_shouldUpdateQuantityAndReturnCartItemResponse() {
        Long cartItemId = 1L;
        Integer newQuantity = 2;
        when(cartItemRepository.findById(cartItemId)).thenReturn(java.util.Optional.of(cartItem));
        when(modelMapper.map(cartItem.getBook(), BookResponse.class)).thenReturn(bookResponse);

        CartItemResponse result = cartItemService.updateCartItemQuantity(cartItemId, newQuantity);

        assertThat(cartItem.getQuantity()).isEqualTo(newQuantity);
        assertThat(result.getBook().getPrice()).isEqualTo(cartItem.getBook().getPrice());
        assertThat(result.getQuantity()).isEqualTo(newQuantity);
    }

    @Test
    void updateCartItemQuantity_shouldThrowCustomNotFoundExceptionWhenCartItemNotFound() {
        Long cartItemId = 1L;
        Integer newQuantity = 2;
        when(cartItemRepository.findById(cartItemId)).thenReturn(java.util.Optional.empty());
        assertThatCode(() -> cartItemService.updateCartItemQuantity(cartItemId, newQuantity)).isInstanceOf(CustomNotFoundException.class);
    }

    @Test
    void testRemoveBookFromCartWithQuantityGreaterOrEqualToCartItemQuantity() {
        CartItem existingItem = CartItem.builder().book(book).shoppingCart(shoppingCart).quantity(6).build();
        existingItem.setId(1L);
        shoppingCart.getItems().add(existingItem);
        cartItem.setQuantity(6);
        when(bookService.findById(1L)).thenReturn(book);
        when(userService.retrieveCurrentLoginUser()).thenReturn(user);
        when(shoppingCartService.retrieveActiveCartForUser(user)).thenReturn(shoppingCart);

        String result = cartItemService.removeBookFromCart(1L, 6);

        assertThat(result).isEqualTo(String.format(SuccessCodes.DELETED_CART_ITEM_SUCCESS.getMessage(), cartItem.getId()));
    }
}