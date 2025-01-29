package app.dto.item.cart;

import lombok.Data;

@Data
public class ItemCartCreateRequestDto {
    private Long bookId;
    private int quantity;
}
