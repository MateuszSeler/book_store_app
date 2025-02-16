package app.dto.shoppingcart;

import app.dto.cartitem.CartItemDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShoppingCartDto {
    @NotNull
    @Positive
    private Long userId;
    private Set<CartItemDto> cartItemDtos;
}
