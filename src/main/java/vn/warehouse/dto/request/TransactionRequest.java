package vn.warehouse.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import vn.warehouse.model.enumuration.TransactionType;

import java.time.LocalDateTime;

@Getter
public class TransactionRequest {
    @NotNull(message = "Số lượng là bắt buộc")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer quantity;

    @NotNull(message = "Loại giao dịch là bắt buộc")
    private TransactionType type;

    @NotBlank(message = "Trạng thái giao dịch là bắt buộc")
    private String status;

    @NotNull(message = "Ngày giao dịch là bắt buộc")
    private LocalDateTime transactionDate;

    @NotNull(message = "ID sản phẩm là bắt buộc")
    private Long productId;

    @NotNull(message = "ID nhân viên là bắt buộc")
    private Long employeeId;

    private Long supplierId;
}