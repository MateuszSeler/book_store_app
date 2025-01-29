package app.dto.shopping.cart;

import app.dto.item.cart.ItemCartDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long userId;
    private Set<ItemCartDto> itemCartDtos;
}
