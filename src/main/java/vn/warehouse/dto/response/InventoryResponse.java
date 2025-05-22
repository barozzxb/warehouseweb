package vn.warehouse.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InventoryResponse {
    private Long id;
    private Long warehouseId;
    private List<Long> productIds = new ArrayList<>();
}