package app.dto.itemcart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCartCreateRequestDto {
    @NotNull
    @NotEmpty
    private Long bookId;
    @Min(1)
    private int quantity;
}
