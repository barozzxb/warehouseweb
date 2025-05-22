package vn.warehouse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SupplierRequest {
    @NotBlank(message = "Tên nhà cung cấp là bắt buộc")
    private String name;

    @NotBlank(message = "Địa chỉ là bắt buộc")
    private String address;

    @NotBlank(message = "Số liên hệ là bắt buộc")
    private String contactNumber;

    @NotNull(message = "ID kho hàng là bắt buộc")
    private Long warehouseId;
}