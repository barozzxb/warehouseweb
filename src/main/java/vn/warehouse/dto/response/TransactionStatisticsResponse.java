package vn.warehouse.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.warehouse.model.enumuration.TransactionType;

@Getter
@Setter
@Builder
public class TransactionStatisticsResponse {
    private TransactionType type;
    private String status;
    private Long totalTransactions;
    private Long totalQuantity;
}
