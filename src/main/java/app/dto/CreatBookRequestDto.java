package app.dto;

import java.math.BigDecimal;

import app.validator.Isbn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatBookRequestDto {
    @NotNull
    @Size(min = 1)
    private String title;
    private String author;
    @Isbn
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
