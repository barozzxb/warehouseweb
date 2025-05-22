package vn.warehouse.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String location;
    private LocalDateTime entryDate;
    private LocalDateTime expiryDate;
    private Long categoryId;
    private Long inventoryId;
    private List<Long> productSupplierIds = new ArrayList<>();
    private List<Long> transactionIds = new ArrayList<>();
}