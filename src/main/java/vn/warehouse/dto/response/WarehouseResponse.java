package vn.warehouse.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WarehouseResponse {
    private Long id;
    private String name;
    private String location;
    private List<Long> supplierIds = new ArrayList<>();
    private List<Long> inventoryIds = new ArrayList<>();
}