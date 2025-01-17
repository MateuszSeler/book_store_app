package app.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateRequestDto {
    @NotNull
    @Size(min = 1)
    private String name;
    private String description;
}
