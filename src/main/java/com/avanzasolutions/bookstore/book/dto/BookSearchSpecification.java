/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.book.dto;

import com.avanzasolutions.bookstore.book.entity.Book;
import com.avanzasolutions.bookstore.common.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Book filter specification request")
public class BookSearchSpecification implements Specification<Book> {

    @Schema(description = "Book ID", example = "1")
    private Long id;

    @Schema(description = "Book name", example = "Harry Potter and the Philosopher's Stone")
    private String name;

    @Schema(description = "Book description", example = "A young boy discovers he's a wizard and attends a school of magic")
    private String description;

    @Schema(description = "Book author", example = "J.K. Rowling")
    private String author;

    @Schema(description = "Book type", example = "Fantasy")
    private String type;

    @Schema(description = "Book price", example = "29.99")
    private Double price;

    @Schema(description = "Book ISBN", example = "9780590353403")
    private String isbn;

    @Override
    public Predicate toPredicate(@NonNull Root<Book> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicateList = new ArrayList<>();

        if (this.id != null)
            predicateList.add(criteriaBuilder.like(root.get("id").as(String.class),
                    Constants.Characters.PERCENTAGE + this.id + Constants.Characters.PERCENTAGE));

        if (this.isbn != null)
            predicateList.add(criteriaBuilder.like(root.get("isbn").as(String.class),
                    Constants.Characters.PERCENTAGE + this.isbn + Constants.Characters.PERCENTAGE));

        if (this.price != null)
            predicateList.add(criteriaBuilder.like(root.get("price").as(String.class),
                    Constants.Characters.PERCENTAGE + this.price + Constants.Characters.PERCENTAGE));

        if (this.name != null)
            predicateList.add(criteriaBuilder.like(root.get("name").as(String.class),
                    Constants.Characters.PERCENTAGE + this.name + Constants.Characters.PERCENTAGE));

        if (this.author != null)
            predicateList.add(criteriaBuilder.like(root.get("author").as(String.class),
                    Constants.Characters.PERCENTAGE + this.author + Constants.Characters.PERCENTAGE));

        if (this.description != null)
            predicateList.add(criteriaBuilder.like(root.get("description").as(String.class),
                    Constants.Characters.PERCENTAGE + this.description + Constants.Characters.PERCENTAGE));

        if (this.type != null)
            predicateList.add(criteriaBuilder.like(root.get("type").as(String.class),
                    Constants.Characters.PERCENTAGE + this.type + Constants.Characters.PERCENTAGE));

        return criteriaBuilder.and(predicateList.toArray(new Predicate[]{}));
    }
}
