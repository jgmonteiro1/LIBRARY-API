package com.jgmonteiro.LibraryAPI.api.resource;

import com.jgmonteiro.LibraryAPI.api.dto.BookDTO;
import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import com.jgmonteiro.LibraryAPI.api.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/api/books")
public class BookControler {

    private BookService service;

    public BookControler(BookService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody BookDTO bookDTO){
        Book entity =
                Book.builder()
                .author(bookDTO.getAuthor())
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .build();

        entity = service.save(entity);
        return BookDTO.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .title(entity.getAuthor())
                .isbn(entity.getIsbn())
                .build();

    }

}
