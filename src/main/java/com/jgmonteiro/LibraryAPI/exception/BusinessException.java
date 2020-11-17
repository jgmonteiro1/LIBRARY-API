package com.jgmonteiro.LibraryAPI.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String isbn_já_cadastrado) {
        super(isbn_já_cadastrado);
    }
}
