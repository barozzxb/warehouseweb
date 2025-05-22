package vn.warehouse.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InventoryStatisticsResponse {
    private Long id; // ID của kho hoặc danh mục
    private String name; // Tên của kho hoặc danh mục
    private Long totalProducts; // Tổng số sản phẩm
    private Integer totalQuantity; // Tổng số lượng tồn kho
    private Double totalValue; // Tổng giá trị tồn kho (dựa trên unitPrice)
}
