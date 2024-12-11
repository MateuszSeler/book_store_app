package app.service;

import app.model.Book;
import java.util.List;

public interface BookService {
    Book addBook(Book book);

    Book getBook(int id);

    List<Book> getAllBooks();

    boolean deleteBook(int id);
}
