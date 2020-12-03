package pl.coderslab.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemoryBookService implements BookService{
    private final List<Book> bookList;
    private static Long nextId = 4L;

    public MemoryBookService() {
        this.bookList = new ArrayList<>();
        this.bookList.add(new Book(1L, "9788324631766", "Thinking in Java", "Bruce Eckel", "Helion", "programming"));
        this.bookList.add(new Book(2L, "9788324627738", "Rusz głową Java.", "Sierra Kathy, Bates Bert", "Helion", "programming"));
        this.bookList.add(new Book(3L, "9780130819338", "Java 2. Podstawy", "Cay Horstmann, Gary Cornell", "Helion", "programming"));
    }

    //Pobieranie listy wszystkich książek
    @Override
    public List<Book> getAllBooks() {
        return this.bookList;
    }

    //Pobieranie książki po wskazanym identyfikatorze
    @Override
    public Optional<Book> getBook(Long id) {
        return this.bookList.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    //Dodawanie książki
    @Override
    public void addBook(Book book) {
        book.setId(nextId);
        this.bookList.add(book);
        nextId++;
    }

    //Edycja książki
    @Override
    public void updateBook(Book book) {
        Long id = book.getId();
        Book optionalBook = this.bookList.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst().orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with id="+id+" exists in the database");
                });
        int index = this.bookList.indexOf(optionalBook);
        this.bookList.set(index, book);
    }

    //Usuwanie książki
    @Override
    public void removeBook(Book book) {
        this.bookList.remove(book);
    }
}
