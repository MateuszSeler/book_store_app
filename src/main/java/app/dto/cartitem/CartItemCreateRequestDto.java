package app.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItemCreateRequestDto {
    @NotNull
    @Positive
    private Long bookId;
    @Min(1)
    private int quantity;
}
