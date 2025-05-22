package vn.warehouse.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.warehouse.model.enumuration.TransactionType;

@Getter
@Setter
@Builder
public class TransactionStatisticsResponse {
    private Long id; // ID của nhân viên hoặc khoảng thời gian (nếu nhóm theo thời gian)
    private String name; // Tên nhân viên hoặc mô tả khoảng thời gian
    private TransactionType type; // Loại giao dịch (nếu lọc theo loại)
    private Long totalTransactions; // Tổng số giao dịch
    private Integer totalQuantity; // Tổng số lượng sản phẩm giao dịch
}
