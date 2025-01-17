package app.dto.book;

import app.validator.Isbn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class BookCreateRequestDto {
    @NotNull
    @Size(min = 1)
    private String title;
    @NotNull
    private String author;
    @Isbn
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<Long> categoriesIds;

}
