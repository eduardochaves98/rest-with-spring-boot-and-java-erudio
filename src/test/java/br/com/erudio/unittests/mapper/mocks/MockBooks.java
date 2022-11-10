package br.com.erudio.unittests.mapper.mocks;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.model.Book;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBooks {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setTitle("Title" + number);
        book.setAuthor("Author" + number);
        book.setPrice(number*10);
        book.setId(number.longValue());
        book.setLaunch_date(new Date(number*1000));
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO bookVO = new BookVO();
        bookVO.setTitle("Title" + number);
        bookVO.setAuthor("Author" + number);
        bookVO.setPrice(number*10);
        bookVO.setKey(number);
        bookVO.setLaunch_date(new Date(number*1000));
        return bookVO;
    }

}
