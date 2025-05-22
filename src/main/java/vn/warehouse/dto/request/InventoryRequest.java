package vn.warehouse.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InventoryRequest {
    @NotNull(message = "ID kho hàng là bắt buộc")
    private Long warehouseId;
}