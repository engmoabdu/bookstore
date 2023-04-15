/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.auth.controller;

import java.util.List;

import com.avanzasolutions.bookstore.auth.dto.UserRequest;
import com.avanzasolutions.bookstore.auth.dto.UserResponse;
import com.avanzasolutions.bookstore.auth.service.UserService;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth Controller")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register new Admin User")
    @PostMapping("/register-admin")
    public ResponseEntity<SuccessResponse<UserResponse>> addAdminUser(@Valid @RequestBody UserRequest userInfo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.<UserResponse>builder()
                        .data(userService.addAdminUser(userInfo))
                        .message("AdminUser added successfully")
                        .build());
    }

    @Operation(summary = "Register new User")
    @PostMapping("/register-user")
    public ResponseEntity<SuccessResponse<UserResponse>> addUser(@Valid @RequestBody UserRequest userInfo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.<UserResponse>builder()
                        .data(userService.addUser(userInfo))
                        .message("User added successfully")
                        .build());
    }

    @Operation(summary = "Delete User", security = {@SecurityRequirement(name = "basicAuth")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<SuccessResponse<String>> deleteUser(@Parameter(name = "userId", in = ParameterIn.QUERY, required = true)
                                                              @RequestParam Integer userId) {
        return ResponseEntity.ok(SuccessResponse.<String>builder().message(userService.deleteUser(userId)).build());
    }

    @Profile("development")
    @Operation(summary = "", security = {@SecurityRequirement(name = "basicAuth")})
    @GetMapping("/is-running")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String isRunning() {
        return "Service is running";
    }

    @Operation(summary = "Get all users", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<SuccessResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(SuccessResponse.<List<UserResponse>>builder().data(userService.getAllUsers()).build());
    }
}