package app.repository.book;

import app.model.Book;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends
        JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {

    @Query("FROM Book book "
            + "left join fetch book.categories "
            + " WHERE book.id = :id")
    Optional<Book> findById(@NonNull Long id);

    @Query("FROM Book book "
            + "left join fetch book.categories")
    List<Book> findAll();

    @Query(value = "SELECT * "
            + "FROM books "
            + "JOIN books_categories "
            + "ON id = books_categories.book_id "
            + "WHERE category_id = :categoryId",
            nativeQuery = true)
    List<Book> findAllByCategoryId(@NonNull Long categoryId);
}
