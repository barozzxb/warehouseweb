package vn.warehouse.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SupplierResponse {
    private Long id;
    private String name;
    private String address;
    private String contactNumber;
    private Long warehouseId;
    private List<Long> productSupplierIds = new ArrayList<>();
    private List<Long> transactionIds = new ArrayList<>();
}