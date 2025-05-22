package vn.warehouse.service;

import vn.warehouse.dto.request.ProductRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    PageResponse<ProductResponse> getAllProducts(int page, int size, String sort, String direction);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);

    PageResponse<ProductResponse> getProductsByCategory(Long categoryId, int page, int size, String sort, String direction);

    PageResponse<ProductResponse> getProductsByInventory(Long inventoryId, int page, int size, String sort, String direction);

    PageResponse<ProductResponse> getExpiringProducts(int page, int size, String sort, String direction, int days);
}