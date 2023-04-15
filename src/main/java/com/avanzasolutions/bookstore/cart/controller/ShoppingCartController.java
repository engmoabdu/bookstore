/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.controller;

import com.avanzasolutions.bookstore.cart.dto.shoppingcart.ShoppingCartResponse;
import com.avanzasolutions.bookstore.cart.service.ShoppingCartService;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Tag(name = "ShoppingCart Controller")
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Create a new shopping cart", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponse(responseCode = "201", description = "Cart created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @PostMapping
    public ResponseEntity<SuccessResponse<ShoppingCartResponse>> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.<ShoppingCartResponse>builder()
                        .message("Cart created successfully").data(shoppingCartService.createCart()).build());
    }

    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @Operation(summary = "Get the shopping cart for the current user", security = {@SecurityRequirement(name = "basicAuth")})
    @GetMapping
    public ResponseEntity<SuccessResponse<ShoppingCartResponse>> getUserCart() {
        return ResponseEntity.ok(SuccessResponse.<ShoppingCartResponse>builder().data(shoppingCartService.getUserCart()).build());
    }
}
