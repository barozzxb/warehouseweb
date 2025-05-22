package vn.warehouse.service;

import vn.warehouse.dto.request.SupplierRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.SupplierResponse;

public interface SupplierService {
    SupplierResponse createSupplier(SupplierRequest request);
    SupplierResponse getSupplierById(Long id);
    PageResponse<SupplierResponse> getAllSuppliers(int page, int size, String sort, String direction);
    SupplierResponse updateSupplier(Long id, SupplierRequest request);
    void deleteSupplier(Long id);
}