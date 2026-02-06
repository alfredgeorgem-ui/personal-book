package com.example.demo.service;

import com.example.demo.db.Book;
import com.example.demo.db.BookRepository;
import com.example.demo.google.GoogleBook;
import com.example.demo.google.GoogleBookService;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final GoogleBookService googleBookService;
    private final BookRepository bookRepository;

    public BookService(GoogleBookService googleBookService, BookRepository bookRepository){
        this.googleBookService = googleBookService;
        this.bookRepository = bookRepository;
    }

    public Book addBook(String googleId){
        try{
            GoogleBook googleBook = googleBookService.getBookById(googleId);
            Book book = mapToBook(googleBook);
            return bookRepository.save(book);
        }
        catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Invalid book id", ex);
        }
    }

    private Book mapToBook(GoogleBook googleBook){
        GoogleBook.Item item = googleBook.items().get(0);
        String author = item.volumeInfo().authors().get(0);
        Book book = new Book();
        book.setId(item.id());
        book.setAuthor(author);
        book.setTitle(item.volumeInfo().title());
        book.setPageCount(item.volumeInfo().pageCount());
        return book;
    }
}
