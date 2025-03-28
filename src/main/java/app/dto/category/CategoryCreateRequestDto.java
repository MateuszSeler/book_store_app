package app.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryCreateRequestDto {
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @Size(max = 5000)
    private String description;
}
