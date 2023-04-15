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
import com.avanzasolutions.bookstore.common.infrastructure.dto.response.PaginationResponse;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequest bookRequest;
    private Book book;
    private BookResponse bookResponse;
    private List<BookRequest> bookRequestList;
    private List<Book> bookList;
    private Page<Book> bookPage;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        bookRequest = new BookRequest();
        bookRequest.setName("The Great Gatsby");
        bookRequest.setDescription("A novel by F. Scott Fitzgerald");
        bookRequest.setAuthor("F. Scott Fitzgerald");
        bookRequest.setType("Novel");
        bookRequest.setPrice(9.99);
        bookRequest.setIsbn("978-3-16-148410-0");
        book = new Book("Test Book", "Test Description", "Test Author", "Test Type", 100.0, "Test ISBN");
        bookResponse = new BookResponse("Test Book", "Test Description", "Test Author", "Test Type", 100.0, "Test ISBN");
        ;
        bookRequestList = List.of(bookRequest);
        bookList = List.of(book);
        bookPage = new PageImpl<>(bookList);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testAddBook() {
        when(modelMapper.map(Mockito.any(BookRequest.class), Mockito.any())).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BookResponse.class)).thenReturn(bookResponse);

        BookResponse actualResponse = bookService.addBook(bookRequest);

        assertThat(actualResponse).isEqualTo(bookResponse);
    }

    @Test
    void testAddListOfBooks() {
        when(modelMapper.map(bookRequest, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        List<BookResponse> expectedResponse = List.of(bookResponse);
        when(modelMapper.map(book, BookResponse.class)).thenReturn(bookResponse);

        List<BookResponse> actualResponse = bookService.addListOfBooks(bookRequestList);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void testGetById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookResponse.class)).thenReturn(bookResponse);

        BookResponse actualResponse = bookService.getById(1L);

        assertThat(actualResponse).isEqualTo(bookResponse);
    }

    @Test
    void testFilter() {
        BookSearchSpecification bookSearchSpecification = new BookSearchSpecification();
        when(bookRepository.findAll(bookSearchSpecification, pageable)).thenReturn(bookPage);
        when(modelMapper.map(bookList, new TypeToken<List<BookResponse>>() {
        }.getType()))
                .thenReturn(List.of(bookResponse));

        PaginationResponse<BookResponse> actualResponse = bookService.filter(bookSearchSpecification, pageable);

        assertThat(actualResponse.getData()).isEqualTo(List.of(bookResponse));
        assertThat(actualResponse.getNumberOfPages()).isEqualTo(bookPage.getTotalPages());
        assertThat(actualResponse.getTotalElements()).isEqualTo(bookPage.getTotalElements());
        assertThat(actualResponse.getPageNumber()).isEqualTo(pageable.getPageNumber() + 1);
        assertThat(actualResponse.getPageSize()).isEqualTo(pageable.getPageSize());
    }

    @Test
    void testFindByIdThrowsNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatCode(() -> bookService.findById(1L)).isInstanceOf(CustomNotFoundException.class);
    }
}