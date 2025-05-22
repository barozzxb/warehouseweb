package vn.warehouse.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ProductSupplierRequest {
    @NotNull(message = "ID sản phẩm là bắt buộc")
    private Long productId;

    @NotNull(message = "ID nhà cung cấp là bắt buộc")
    private Long supplierId;

    @NotNull(message = "Ngày cung cấp là bắt buộc")
    private LocalDate supplyDate;

    @NotNull(message = "Giá cung cấp là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá cung cấp phải lớn hơn 0")
    private BigDecimal supplyPrice;

    @NotNull(message = "Số lượng cung cấp là bắt buộc")
    @Min(value = 1, message = "Số lượng cung cấp phải lớn hơn 0")
    private Integer supplyQuantity;
}