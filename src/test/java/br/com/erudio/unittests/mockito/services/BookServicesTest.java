package br.com.erudio.unittests.mockito.services;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import br.com.erudio.services.BookServices;
import br.com.erudio.unittests.mapper.mocks.MockBooks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBooks input;

    @InjectMocks
    private BookServices service;
    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockBooks();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var books = service.findAll();
        assertNotNull(books);
        assertEquals(14, books.size());

//        people
//                .forEach(p -> {
//                    assertTrue(p.toString().contains("</api/person/v1/" + p.getId() +">"));
//                    assertEquals("Addres Test" + p.getId(),p.getAddress());
//                    assertEquals("First Name Test" + p.getId(),p.getFirstName());
//                    assertEquals("Last Name Test" + p.getId(),p.getLastName());
//                    assertEquals(((p.getId() % 2)==0) ? "Male" : "Female",p.getGender());
//                });
        for (int i = 0; i < 14; i++) {
            BookVO p = books.get(i);
            assertTrue(p.toString().contains("</api/books/v1/" + i +">"));
            assertEquals("Title" + i,p.getTitle());
            assertEquals("Author" + i,p.getAuthor());
            assertEquals(i*10,p.getPrice());
            assertEquals(new Date(i*1000),p.getLaunch_date());
        }

    }

    @Test
    void findById() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result.toString());
        assertTrue(result.toString().contains("</api/books/v1/1>"));
        assertEquals("Title1", result.getTitle());
        assertEquals("Author1", result.getAuthor());
        assertEquals(10,result.getPrice());
        assertEquals(new Date(1000),result.getLaunch_date());
    }

    @Test
    void createBook() {
        Book entity = input.mockEntity(1);
        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.createBook(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Title1", result.getTitle());
        assertEquals("Author1", result.getAuthor());
        assertEquals(10,result.getPrice());
        assertEquals(new Date(1000),result.getLaunch_date());

    }

    @Test
    void createNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,() ->{
            service.createBook(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateBook() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateBook(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Title1", result.getTitle());
        assertEquals("Author1", result.getAuthor());
        assertEquals(10,result.getPrice());
        assertEquals(new Date(1000),result.getLaunch_date());
    }

    @Test
    void updateNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,() ->{
            service.updateBook(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deletePerson() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.deleteBook(1L);

    }
}