package vn.warehouse.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CategoryRequest {
    @NotEmpty(message = "Category name is required")
    private String name;

    private String description;
}
