package app.dto.book;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookInTheCartDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
}
