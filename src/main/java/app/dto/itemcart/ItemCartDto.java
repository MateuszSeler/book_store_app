package app.dto.itemcart;

import app.dto.book.BookDto;
import lombok.Data;

@Data
public class ItemCartDto {
    private Long id;
    private BookDto bookDto;
    private int quantity;
}
