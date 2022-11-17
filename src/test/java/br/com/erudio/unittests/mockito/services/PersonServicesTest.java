package br.com.erudio.unittests.mockito.services;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonServices;
import br.com.erudio.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest{

    MockPerson input;

    @InjectMocks
    private PersonServices service;
    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var people = service.findAll();
        assertNotNull(people);
        assertEquals(14, people.size());

//        people
//                .forEach(p -> {
//                    assertTrue(p.toString().contains("</api/person/v1/" + p.getId() +">"));
//                    assertEquals("Addres Test" + p.getId(),p.getAddress());
//                    assertEquals("First Name Test" + p.getId(),p.getFirstName());
//                    assertEquals("Last Name Test" + p.getId(),p.getLastName());
//                    assertEquals(((p.getId() % 2)==0) ? "Male" : "Female",p.getGender());
//                });
        for (int i = 0; i < 14; i++) {
            PersonVO p = people.get(i);
            assertTrue(p.toString().contains("</api/person/v1/" + i +">"));
            assertEquals("Addres Test" + i,p.getAddress());
            assertEquals("First Name Test" + i,p.getFirstName());
            assertEquals("Last Name Test" + i,p.getLastName());
            assertEquals(((i % 2)==0) ? "Male" : "Female",p.getGender());
        }

    }

    @Test
    void findById() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result.toString());
        assertTrue(result.toString().contains("</api/person/v1/1>"));
        assertEquals("Addres Test1",result.getAddress());
        assertEquals("First Name Test1",result.getFirstName());
        assertEquals("Last Name Test1",result.getLastName());
        assertEquals("Female",result.getGender());
    }

    @Test
    void createPerson() {
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.createPerson(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1",result.getAddress());
        assertEquals("First Name Test1",result.getFirstName());
        assertEquals("Last Name Test1",result.getLastName());
        assertEquals("Female",result.getGender());

    }

    @Test
    void createNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,() ->{
            service.createPerson(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updatePerson() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updatePerson(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1",result.getAddress());
        assertEquals("First Name Test1",result.getFirstName());
        assertEquals("Last Name Test1",result.getLastName());
        assertEquals("Female",result.getGender());
    }

    @Test
    void updateNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,() ->{
            service.updatePerson(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deletePerson() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.deletePerson(1L);

    }
}