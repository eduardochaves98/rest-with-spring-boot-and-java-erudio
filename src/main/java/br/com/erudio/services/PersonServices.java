package br.com.erudio.services;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll(){

        logger.info("Finding all people!!");

        return repository.findAll();
    }

    public Person findById(String id){
        logger.info("Finding one person");

        return repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
    }


    public Person createPerson(Person person) {
        logger.info("Creating one person");
        return repository.save(person);
    }

    public Person updatePerson(Person person) {
        logger.info("Updating one person");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLasttName(person.getLasttName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return repository.save(entity);
    }

    public void deletePerson(String id) {
        logger.info("Deleting one person");
        Person entity = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
        repository.delete(entity);
    }
}
