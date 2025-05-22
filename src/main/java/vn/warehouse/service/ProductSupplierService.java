package vn.warehouse.service;

import vn.warehouse.dto.request.ProductSupplierRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.ProductSupplierResponse;

public interface ProductSupplierService {
    ProductSupplierResponse createProductSupplier(ProductSupplierRequest request);
    ProductSupplierResponse getProductSupplierById(Long id);
    PageResponse<ProductSupplierResponse> getAllProductSuppliers(int page, int size, String sort, String direction);
    ProductSupplierResponse updateProductSupplier(Long id, ProductSupplierRequest request);
    void deleteProductSupplier(Long id);
}