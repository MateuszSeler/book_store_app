package app.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreatBookRequestDto {
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
