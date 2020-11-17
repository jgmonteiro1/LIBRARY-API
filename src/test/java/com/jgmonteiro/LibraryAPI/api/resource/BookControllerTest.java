package com.jgmonteiro.LibraryAPI.api.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgmonteiro.LibraryAPI.api.dto.BookDTO;
import com.jgmonteiro.LibraryAPI.api.model.entity.Book;
import com.jgmonteiro.LibraryAPI.api.service.BookService;
import com.jgmonteiro.LibraryAPI.exception.BusinessException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService service;



    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    public void createBookTest() throws Exception {

        BookDTO dto = createNewBook();
        Book savedBook = Book.builder().id(10l).author("Artur").title("As aventuras").isbn("001").build();

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect( status().isCreated() )
                .andExpect( jsonPath("id").value(10l) )
                .andExpect( jsonPath("title").value(dto.getTitle()) )
                .andExpect( jsonPath("author").value(dto.getAuthor()) )
                .andExpect( jsonPath("isbn").value(dto.getIsbn()) )

        ;

    }



    @Test
    public void verificandoSeOJsonFoiCriado() throws JsonProcessingException {
        BookDTO dto = BookDTO.builder().author("João").title("Livro1").isbn("001").build();
        Book savedBook = Book.builder().id(10l).author("João").title("Livro1").isbn("001").build();

        String json = new ObjectMapper().writeValueAsString(dto);
        System.out.println(json);
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficiente para criação do livro")
    public void createInvalidBookTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("errors", Matchers.hasSize(3)));
    }


    @Test
    @DisplayName("Deve lançar exception ao tentar cadastrar livro com ISBN já cadastrado")
    public void createInvalidBookWithDuplicatedIsbn() throws Exception{

        BookDTO newBook = createNewBook();
        String json = new ObjectMapper().writeValueAsString(newBook);
        BDDMockito.given(service.save(Mockito.any(Book.class))).willThrow(new BusinessException("Isbn já cadastrado"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Isbn já cadastrado"));
    }

    private BookDTO createNewBook() {
        return BookDTO.builder().author("Artur").title("As aventuras").isbn("001").build();
    }
}
