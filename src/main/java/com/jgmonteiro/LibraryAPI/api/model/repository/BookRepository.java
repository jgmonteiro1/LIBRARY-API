package com.jgmonteiro.LibraryAPI.api.model.repository;

import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
