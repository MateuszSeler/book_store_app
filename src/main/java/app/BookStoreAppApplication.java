package app;

import app.model.Book;
import app.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreAppApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book1 = new Book();
                book1.setTitle("Book 1");
                book1.setAuthor("Author 1");
                book1.setIsbn("ISBN 1");
                book1.setPrice(BigDecimal.valueOf(1));
                book1.setDescription("Description 1");
                book1.setCoverImage("img1");

                bookService.addBook(book1);
                bookService.getBook(1);
            }
        };
    }
}
