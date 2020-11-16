package com.jgmonteiro.LibraryAPI.api.resource;

import com.jgmonteiro.LibraryAPI.api.dto.BookDTO;
import com.jgmonteiro.LibraryAPI.api.exceptions.ApiErrors;
import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import com.jgmonteiro.LibraryAPI.api.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
class BookController {

    private final BookService service;
    private final ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create( @RequestBody @Valid BookDTO dto ){
        Book entity = modelMapper.map( dto, Book.class );
        entity = service.save(entity);
        return modelMapper.map(entity, BookDTO.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        return new ApiErrors(bindingResult);
    }
}
