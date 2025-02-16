package app.dto.orderitem;

import app.dto.book.BookDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItemDto {
    @NotNull
    @NotEmpty
    private Long id;
    @NotNull
    private BookDto bookDto;
    @Min(1)
    private int quantity;
    @DecimalMin("0.00")
    private BigDecimal price;
}
