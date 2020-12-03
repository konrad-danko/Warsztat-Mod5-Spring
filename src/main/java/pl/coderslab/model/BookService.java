package pl.coderslab.model;

import java.util.List;
import java.util.Optional;

public interface BookService {

    //Pobieranie listy wszystkich książek
    List<Book> getAllBooks();

    //Pobieranie książki po wskazanym identyfikatorze
    Optional<Book> getBook(Long id);

    //Dodawanie książki
    void addBook(Book book);

    //Edycja książki
    void updateBook(Book book);

    //Usuwanie książki
    void removeBook(Book book);

}
