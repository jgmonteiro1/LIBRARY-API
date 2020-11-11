package com.jgmonteiro.LibraryAPI.api.service;

import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    private BookService service;

    @Test
    @DisplayName("Deve salvar o livro")
    public void saveBookTest(){
        //cenario
        Book book = Book.builder().author("João").isbn("123").title("Livro").build();

        //execucao
       Book savedBook = service.save(book);

       //verificação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getAuthor()).isEqualTo("João");
        assertThat(savedBook.getTitle()).isEqualTo("Livro");
    }
}
