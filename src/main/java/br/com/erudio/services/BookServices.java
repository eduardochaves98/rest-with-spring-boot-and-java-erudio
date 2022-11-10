package br.com.erudio.services;

import br.com.erudio.controllers.BookController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {
    private final Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookVO> findAll(){

        logger.info("Finding all books!!");

        var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findByID(String.valueOf(p.getKey()))).withSelfRel()));

        return books;
    }

    public BookVO findById(Long id) {
        logger.info("Finding one person");

        Book entity =  repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
        BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findByID(Long.toString(id))).withSelfRel());
        return vo;
    }


    public BookVO createBook(BookVO book) {
        if(book == null) throw new RequiredObjectIsNullException();
        logger.info("Creating one person");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findByID(String.valueOf(vo.getKey()))).withSelfRel());
        return vo;
    }

    public BookVO updateBook(BookVO book) {
        if(book == null) throw new RequiredObjectIsNullException();
        logger.info("Updating one person");
        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        entity.setLaunch_date(book.getLaunch_date());
        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findByID(String.valueOf(vo.getKey()))).withSelfRel());
        return vo;
    }

    public void deleteBook(Long id) {
        logger.info("Deleting one person");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records foud for this ID!"));
        repository.delete(entity);
    }
}
