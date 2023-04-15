/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.book.service.impl;

import com.avanzasolutions.bookstore.book.dto.BookRequest;
import com.avanzasolutions.bookstore.book.dto.BookResponse;
import com.avanzasolutions.bookstore.book.dto.BookSearchSpecification;
import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.book.repository.BookRepository;
import com.avanzasolutions.bookstore.book.service.BookService;
import com.avanzasolutions.bookstore.common.enums.ErrorCodes;
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.PaginationResponse;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        bookRequest.setType(bookRequest.getType().toLowerCase()); // set type to lower case first
        return modelMapper.map(bookRepository.save(modelMapper.map(bookRequest, Book.class)), BookResponse.class);
    }

    @Override
    public List<BookResponse> addListOfBooks(List<BookRequest> bookRequestList) {
        return bookRequestList.stream().map(this::addBook).toList();
    }

    @Override
    public BookResponse getById(Long bookId) {
        return modelMapper.map(findById(bookId), BookResponse.class);
    }

    @Override
    public PaginationResponse<BookResponse> filter(BookSearchSpecification bookSearchSpecification, Pageable pageable) {
        Page<Book> page = bookRepository.findAll(bookSearchSpecification, pageable);
        log.info("Total pages: {}", page.getTotalPages());
        return PaginationResponse.<BookResponse>builder()
                .data(this.modelMapper.map(page.getContent(), new TypeToken<List<BookResponse>>() {
                }.getType())).numberOfPages(page.getTotalPages()).totalElements(page.getTotalElements())
                .pageNumber(pageable.getPageNumber() + 1).pageSize(pageable.getPageSize()).build();
    }

    @Override
    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomNotFoundException(
                        ErrorCodes.NOT_FOUND_ERR.getErrorCode(),
                        String.format(ErrorCodes.NOT_FOUND_ERR.getMessage(), bookId)));
    }
}
