package com.jgmonteiro.LibraryAPI.api.service;

import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import com.jgmonteiro.LibraryAPI.api.model.repository.BookRepository;
import com.jgmonteiro.LibraryAPI.api.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    private BookService service;
    @MockBean
    private BookRepository repository;

    @BeforeEach
    public void setup(){
        this.service = new BookServiceImpl(repository);

    }

    @Test
    @DisplayName("Deve salvar o livro")
    public void saveBookTest(){
        //cenario
        Book book = Book.builder().author("João").isbn("123").title("Livro").build();
        Mockito.when(repository.save(book)).thenReturn(Book.builder().id(1l).isbn("123").author("João").title("Livro").build());

        //execucao
       Book savedBook = service.save(book);

       //verificação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getAuthor()).isEqualTo("João");
        assertThat(savedBook.getTitle()).isEqualTo("Livro");
    }
}
