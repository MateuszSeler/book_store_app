package app.dto.item.cart;

import app.dto.book.BookDto;
import lombok.Data;

@Data
public class ItemCartDto {
    private Long id;
    private BookDto bookDto;
    private int quantity;
}
