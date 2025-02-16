package app.dto.book;

import app.validator.Isbn;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookCreateRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @Isbn
    private String isbn;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<Long> categoriesIds;

}
