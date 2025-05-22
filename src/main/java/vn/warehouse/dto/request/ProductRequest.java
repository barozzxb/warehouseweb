package vn.warehouse.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm là bắt buộc")
    private String name;

    @NotNull(message = "Số lượng là bắt buộc")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private Integer quantity;

    @NotNull(message = "Đơn giá là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Đơn giá phải lớn hơn 0")
    private BigDecimal unitPrice;

    private String location;

    @NotNull(message = "Ngày nhập kho là bắt buộc")
    private LocalDateTime entryDate;

    @NotNull(message = "Ngày hết hạn là bắt buộc")
    private LocalDateTime expiryDate;

    @NotNull(message = "ID danh mục là bắt buộc")
    private Long categoryId;

    @NotNull(message = "ID kho là bắt buộc")
    private Long inventoryId;
}