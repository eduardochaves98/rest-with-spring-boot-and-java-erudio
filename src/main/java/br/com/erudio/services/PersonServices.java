package br.com.erudio.services;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
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

    public List<PersonVO> findAll(){

        logger.info("Finding all people!!");

        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(String id){
        logger.info("Finding one person");

        var entitiy =  repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
        return DozerMapper.parseObject(entitiy, PersonVO.class);
    }


    public PersonVO createPerson(PersonVO person) {
        logger.info("Creating one person");
        var entity = DozerMapper.parseObject(person, Person.class);
        return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public PersonVO updatePerson(PersonVO person) {
        logger.info("Updating one person");
        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public void deletePerson(String id) {
        logger.info("Deleting one person");
        var entity = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
        repository.delete(entity);
    }
}
