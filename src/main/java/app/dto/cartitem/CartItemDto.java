package app.dto.cartitem;

import app.dto.book.BookDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemDto {
    @NotNull
    @Positive
    private Long id;
    private BookDto bookDto;
    @Min(1)
    private int quantity;
}
