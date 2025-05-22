package vn.warehouse.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ProductSupplierResponse {
    private Long id;
    private Long productId;
    private Long supplierId;
    private LocalDate supplyDate;
    private BigDecimal supplyPrice;
    private Integer supplyQuantity;
}