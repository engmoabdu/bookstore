/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.controller;

import com.avanzasolutions.bookstore.cart.dto.checkout.CheckoutResponse;
import com.avanzasolutions.bookstore.cart.service.CheckoutService;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/checkout")
@Tag(name = "Checkout Controller")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkService;

    @Operation(
            summary = "Checkout shopping cart", security = {@SecurityRequirement(name = "basicAuth")},
            description = "Calculates the total payable amount for the user's shopping cart after applying any applicable discounts"
    )
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @PostMapping
    public ResponseEntity<SuccessResponse<CheckoutResponse>> checkout(@RequestParam(required = false) String promoCode) {
        return ResponseEntity.ok(SuccessResponse.<CheckoutResponse>builder().data(checkService.checkout(promoCode)).build());
    }
}
