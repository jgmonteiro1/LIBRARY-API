package com.jgmonteiro.LibraryAPI.api.resource;

import com.jgmonteiro.LibraryAPI.api.dto.BookDTO;
import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import com.jgmonteiro.LibraryAPI.api.service.BookService;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    public BookControler(BookService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody BookDTO bookDTO){
        Book entity = modelMapper.map(bookDTO, Book.class);
        entity = service.save(entity);
        return modelMapper.map(entity, BookDTO.class);

    }

}
