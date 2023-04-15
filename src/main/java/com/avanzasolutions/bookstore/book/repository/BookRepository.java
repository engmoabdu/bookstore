/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.book.repository;

import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.common.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends BaseRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
