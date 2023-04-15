/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.controller;

import com.avanzasolutions.bookstore.cart.dto.cartitem.CartItemRequest;
import com.avanzasolutions.bookstore.cart.dto.cartitem.CartItemResponse;
import com.avanzasolutions.bookstore.cart.service.CartItemService;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cart-item")
@Tag(name = "CartItem Controller")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @Operation(summary = "Add a book to the cart", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @PostMapping
    public ResponseEntity<SuccessResponse<String>> addBookToCart(@RequestBody CartItemRequest cartItemRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.<String>builder()
                        .data(cartItemService.addBookToCart(cartItemRequest))
                        .build());
    }

    @Operation(summary = "Remove a book from the cart", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @DeleteMapping("/{bookId}")
    public ResponseEntity<SuccessResponse<String>> removeBookFromCart(@Parameter(name = "bookId", in = ParameterIn.PATH, required = true)
                                                                      @PathVariable Long bookId,
                                                                      @Parameter(name = "quantity", in = ParameterIn.QUERY)
                                                                      @RequestParam(required = false) Integer quantity) {
        return ResponseEntity.ok(SuccessResponse.<String>builder().data(cartItemService.removeBookFromCart(bookId, quantity)).build());
    }

    @Operation(summary = "Update the quantity of a book in the cart", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @PutMapping("/{cartItemId}/{newQuantity}")
    public ResponseEntity<SuccessResponse<CartItemResponse>> updateCartItemQuantity(@Parameter(name = "cartItemId", in = ParameterIn.PATH, required = true) @PathVariable Long cartItemId,
                                                                                    @Parameter(name = "newQuantity", in = ParameterIn.PATH, required = true) @PathVariable Integer newQuantity) {
        return ResponseEntity.ok(SuccessResponse.<CartItemResponse>builder().data(cartItemService.updateCartItemQuantity(cartItemId, newQuantity)).build());
    }
}

