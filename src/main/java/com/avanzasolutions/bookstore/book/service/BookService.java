/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.book.service;

import com.avanzasolutions.bookstore.book.dto.BookRequest;
import com.avanzasolutions.bookstore.book.dto.BookResponse;
import com.avanzasolutions.bookstore.book.dto.BookSearchSpecification;
import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.PaginationResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    BookResponse addBook(BookRequest bookRequest);

    List<BookResponse> addListOfBooks(List<BookRequest> bookRequestList);

    BookResponse getById(Long bookId);

    PaginationResponse<BookResponse> filter(BookSearchSpecification bookSearchSpecification, Pageable pageable);

    Book findById(Long bookId);
}
