package app.dto.shoppingcart;

import app.dto.itemcart.ItemCartDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long userId;
    private Set<ItemCartDto> itemCartDtos;
}
