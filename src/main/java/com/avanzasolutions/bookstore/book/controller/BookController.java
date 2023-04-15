/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.book.controller;

import com.avanzasolutions.bookstore.book.dto.BookRequest;
import com.avanzasolutions.bookstore.book.dto.BookResponse;
import com.avanzasolutions.bookstore.book.dto.BookSearchSpecification;
import com.avanzasolutions.bookstore.book.service.BookService;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.PaginationResponse;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/books")
@Tag(name = "Book Controller")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Add new Book", security = {@SecurityRequirement(name = "basicAuth")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<BookResponse>> addBook(@Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.<BookResponse>builder()
                        .data(bookService.addBook(bookRequest))
                        .message("Book added successfully")
                        .build());
    }

    @Operation(summary = "Add new List of Books", security = {@SecurityRequirement(name = "basicAuth")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/list")
    public ResponseEntity<SuccessResponse<List<BookResponse>>> addListOfBooks(@Valid @RequestBody List<BookRequest> bookRequestList) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.<List<BookResponse>>builder()
                        .data(bookService.addListOfBooks(bookRequestList))
                        .message("Books added successfully")
                        .build());
    }

    @Operation(summary = "Get a book by bookId", security = {@SecurityRequirement(name = "basicAuth")})
    @GetMapping("/{bookId}")
    public ResponseEntity<SuccessResponse<BookResponse>> getById(@Parameter(name = "bookId", in = ParameterIn.PATH, required = true)
                                                                 @PathVariable Long bookId) {
        return ResponseEntity.ok(SuccessResponse.<BookResponse>builder().data(bookService.getById(bookId)).build());
    }

    @Operation(summary = "Get all books using search API", security = {@SecurityRequirement(name = "basicAuth")})
    @PageableAsQueryParam
    @PostMapping("/search")
    public ResponseEntity<PaginationResponse<BookResponse>> filter(@RequestBody BookSearchSpecification bookSearchSpecification,
                                                                   @PageableDefault(size = 20, page = 0) Pageable pageable) {
        return ResponseEntity.ok(this.bookService.filter(bookSearchSpecification, pageable));
    }
}
