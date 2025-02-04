package app.dto.orderitem;

import app.dto.book.BookDto;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private BookDto bookDto;
    private int quantity;
    private BigDecimal price;
}
