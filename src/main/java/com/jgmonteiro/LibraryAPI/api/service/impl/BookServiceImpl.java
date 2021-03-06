package com.jgmonteiro.LibraryAPI.api.service.impl;

import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import com.jgmonteiro.LibraryAPI.api.model.repository.BookRepository;
import com.jgmonteiro.LibraryAPI.api.service.BookService;
import com.jgmonteiro.LibraryAPI.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {


    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        if(repository.existsByIsbn(book.getIsbn())){
            throw new BusinessException("Isbn já cadastrado");
        }
        return repository.save(book);
    }
}
