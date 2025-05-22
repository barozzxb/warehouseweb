package vn.warehouse.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.warehouse.model.enumuration.TransactionType;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private Integer quantity;
    private TransactionType type;
    private String status;
    private LocalDateTime transactionDate;
    private Long productId;
    private Long employeeId;
    private Long supplierId;
}