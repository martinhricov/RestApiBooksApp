package com.example.restpapi.controller;

import com.example.restpapi.model.Book;
import com.example.restpapi.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    @GetMapping("book/{id}")
    public Optional<Book> getBook(@PathVariable("id") Long id) {
        return bookRepository.findById(id);

    }
    @PostMapping()
    public ResponseEntity createBook(@RequestBody Book book) throws URISyntaxException {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.created(new URI("/book" + savedBook.getId())).body(savedBook);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateBook(@PathVariable Long id, @RequestBody Book book){
        Book currentBookData = bookRepository.findById(id).orElseThrow(RuntimeException ::new);
        currentBookData.setName(book.getName());
        currentBookData.setAuthor(book.getAuthor());
        currentBookData.setCategory(book.getCategory());
        currentBookData.setWasRead(book.isWasRead());
        currentBookData = bookRepository.save(book);

        return ResponseEntity.ok(currentBookData);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteBookById(@PathVariable Long id){
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
