package br.com.erudio.controllers;


import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.PersonServices;
import br.com.erudio.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_YML})
    public List<PersonVO> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public PersonVO findByID(@PathVariable(value = "id") String id){
            return service.findById(Long.valueOf(id));
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public PersonVO createPerson(@RequestBody() PersonVO person){
        return service.createPerson(person);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public PersonVO updatePerson(@RequestBody() PersonVO person){
        return service.updatePerson(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable(value = "id") String id){
        service.deletePerson(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }

}
