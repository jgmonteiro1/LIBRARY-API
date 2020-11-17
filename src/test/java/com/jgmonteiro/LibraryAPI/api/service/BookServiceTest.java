package com.jgmonteiro.LibraryAPI.api.service;

import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import com.jgmonteiro.LibraryAPI.api.model.repository.BookRepository;
import com.jgmonteiro.LibraryAPI.api.service.impl.BookServiceImpl;
import com.jgmonteiro.LibraryAPI.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.anyOf;
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
        Book book = createBook();
        Mockito.when( repository.save(book)).thenReturn(Book.builder().id(1l).isbn("123").author("João").title("Livro").build());
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
        //execucao
       Book savedBook = service.save(book);

       //verificação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getAuthor()).isEqualTo("João");
        assertThat(savedBook.getTitle()).isEqualTo("Livro");
    }

    @Test
    @DisplayName("Deve lançar erro de negócio ao tentar salvar ISBN duplicado")
    public void shouldNotSaveBookWithDuplicatedIsbn(){
        Book book = createBook();
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
        Throwable exception = Assertions.catchThrowable(() -> service.save(book));
        assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("Isbn já cadastrado");
        Mockito.verify(repository, Mockito.never()).save(book);

    }

    private Book createBook() {
        return Book.builder().author("João").isbn("123").title("Livro").build();
  }
}
