package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.model.Book;
import pl.coderslab.model.MemoryBookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final MemoryBookService memoryBookService;

    @Autowired
    public BookController(MemoryBookService memoryBookService) {
        this.memoryBookService = memoryBookService;
    }

    //Pobieranie listy wszystkich książek
    @GetMapping(path = "")
    private List<Book> getAllBooksAction() {
        return memoryBookService.getAllBooks();
    }

    //Pobieranie książki po wskazanym identyfikatorze
    @GetMapping(path = "/{id}")
    private Book getBookAction(@PathVariable Long id) {
        return memoryBookService.getBook(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with id="+id+" exists in the database");
        });
    }

    //Dodawanie książki
    @PostMapping(path = "")
    private void addBookAction(@RequestParam String isbn,
                               @RequestParam String title,
                               @RequestParam String author,
                               @RequestParam String publisher,
                               @RequestParam String type) {
        Long id = memoryBookService.getNextId();
        Book book = new Book(id, isbn, title, author, publisher, type);
        memoryBookService.addBook(book);
        memoryBookService.setNextId(id + 1);
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
        memoryBookService.updateBook(book);
    }

    //Usuwanie książki
    @DeleteMapping(path = "/{id}")
    private void removeBookAction(@PathVariable Long id) {
        Book book = memoryBookService.getBook(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with id="+id+" exists in the database");
        });
        memoryBookService.removeBook(book);
    }
}
