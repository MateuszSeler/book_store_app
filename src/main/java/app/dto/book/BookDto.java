package app.dto.book;

import app.validator.Isbn;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookDto {
    @NotNull
    @Positive
    private Long id;
    private String title;
    private String author;
    @Isbn
    private String isbn;
    @DecimalMin("0.00")
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<String> categoriesNames;
}
