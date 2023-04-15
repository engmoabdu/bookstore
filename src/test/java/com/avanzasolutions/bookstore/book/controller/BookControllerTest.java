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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    private BookRequest bookRequest;
    private BookResponse bookResponse;


    @BeforeEach
    void setUp() {
        bookRequest = BookRequest.builder()
                .name("The Great Gatsby")
                .description("A novel by F. Scott Fitzgerald")
                .author("F. Scott Fitzgerald")
                .type("Fiction")
                .price(20.99)
                .isbn("978-3-16-148410-0")
                .build();

        bookResponse = BookResponse.builder()
                .id(1L)
                .name("The Great Gatsby")
                .description("A novel by F. Scott Fitzgerald")
                .author("F. Scott Fitzgerald")
                .type("Fiction")
                .price(20.99)
                .isbn("978-3-16-148410-0")
                .build();
    }

    @Test
    void testAddBook() {
        Mockito.when(bookService.addBook(bookRequest)).thenReturn(bookResponse);
        ResponseEntity<SuccessResponse<BookResponse>> result = bookController.addBook(bookRequest);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testAddListOfBooks() {
        ResponseEntity<SuccessResponse<List<BookResponse>>> result = bookController.addListOfBooks(List.of(bookRequest));
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testGetById() {
        ResponseEntity<SuccessResponse<BookResponse>> result = bookController.getById(Long.valueOf(1));
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testFilter() {
        ResponseEntity<PaginationResponse<BookResponse>> result = bookController.filter(new BookSearchSpecification(1L, "name", "description", "author", "type", Double.valueOf(0), "isbn"), null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}