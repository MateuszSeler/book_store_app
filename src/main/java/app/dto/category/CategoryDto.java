package app.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CategoryDto {
    @NotNull
    @Positive
    private Long id;
    private String name;
    private String description;
}
