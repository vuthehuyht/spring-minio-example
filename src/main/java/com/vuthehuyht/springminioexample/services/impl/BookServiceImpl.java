package com.vuthehuyht.springminioexample.services.impl;

import com.vuthehuyht.springminioexample.models.Book;
import com.vuthehuyht.springminioexample.repositories.BookRepository;
import com.vuthehuyht.springminioexample.services.BookService;
import com.vuthehuyht.springminioexample.services.ImageStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ImageStoreService imageStoreService;

    @Override
    public Book saveBook(Book book, MultipartFile imageFile) {
        try {
            String imageUrl = imageStoreService.uploadImage(imageFile);
            book.setImageUrl(imageUrl);
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save book.", e);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
