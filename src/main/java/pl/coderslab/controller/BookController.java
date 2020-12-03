package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.model.Book;
import pl.coderslab.model.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Pobieranie listy wszystkich książek
    @GetMapping(path = "")
    private List<Book> getAllBooksAction() {
        return bookService.getAllBooks();
    }

    //Pobieranie książki po wskazanym identyfikatorze
    @GetMapping(path = "/{id}")
    private Book getBookAction(@PathVariable Long id) {
        return bookService.getBook(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with id=" + id + " exists in the database");
        });
    }

    //Dodawanie książki
    @PostMapping(path = "")
    private void addBookAction(@RequestParam String isbn,
                               @RequestParam String title,
                               @RequestParam String author,
                               @RequestParam String publisher,
                               @RequestParam String type) {
        Book book = new Book(isbn, title, author, publisher, type);
        bookService.addBook(book);
    }

    //Edycja książki
    @PutMapping(path = "")
    private void updateBookAction(@RequestParam Long id,
                                  @RequestParam String isbn,
                                  @RequestParam String title,
                                  @RequestParam String author,
                                  @RequestParam String publisher,
                                  @RequestParam String type) {
        Book book = new Book(id, isbn, title, author, publisher, type);
        bookService.updateBook(book);
    }

    //Usuwanie książki
    @DeleteMapping(path = "/{id}")
    private void removeBookAction(@PathVariable Long id) {
        Book book = bookService.getBook(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with id=" + id + " exists in the database");
        });
        bookService.removeBook(book);
    }
}
