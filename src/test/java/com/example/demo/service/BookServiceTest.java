package com.example.demo.service;

import com.example.demo.db.Book;
import com.example.demo.db.BookRepository;
import com.example.demo.google.GoogleBook;
import com.example.demo.google.GoogleBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private GoogleBookService googleBookService;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    void addBookWithValidId(){
        String googleId = "g123";
        GoogleBook.VolumeInfo volumeInfo = new GoogleBook.VolumeInfo("Effective Java",
                List.of("Joshua"), "2018", "Addison", 416, "Book", "Not_Mature", List.of("Adult"), "eng", "", "");
        GoogleBook.Item item = new GoogleBook.Item(googleId, "self link", volumeInfo, null);
        GoogleBook googleBook = new GoogleBook("books", 1, List.of(item));
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setId(googleId);
        book.setAuthor("Joshua");
        book.setPageCount(416);

        when(googleBookService.getBookById(googleId)).thenReturn(googleBook);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.addBook(googleId);
        assertNotNull(result);
        assertEquals("Effective Java", result.getTitle());
    }
}