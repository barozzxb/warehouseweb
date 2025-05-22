package vn.warehouse.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private List<Long> productIds = new ArrayList<>();
}
