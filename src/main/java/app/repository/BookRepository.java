package app.repository;

import app.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> get(int id);

    List<Book> findAll();

    boolean deleteById(int id);
}
