/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.book.entity;

import com.avanzasolutions.bookstore.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false, unique = true)
    private String isbn;
}
