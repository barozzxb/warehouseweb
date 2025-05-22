package vn.warehouse.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WarehouseRequest {
    @NotBlank(message = "Tên kho hàng là bắt buộc")
    private String name;

    @NotBlank(message = "Vị trí kho hàng là bắt buộc")
    private String location;
}