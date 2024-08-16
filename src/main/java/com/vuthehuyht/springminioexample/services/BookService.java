package com.vuthehuyht.springminioexample.services;

import com.vuthehuyht.springminioexample.models.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    Book saveBook(Book book, MultipartFile imageFile);
    List<Book> getAllBooks();
}
