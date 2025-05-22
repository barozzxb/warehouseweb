package vn.warehouse.service;

import vn.warehouse.dto.request.WarehouseRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.WarehouseResponse;

public interface WarehouseService {
    WarehouseResponse createWarehouse(WarehouseRequest request);
    WarehouseResponse getWarehouseById(Long id);
    PageResponse<WarehouseResponse> getAllWarehouses(int page, int size, String sort, String direction);
    WarehouseResponse updateWarehouse(Long id, WarehouseRequest request);
    void deleteWarehouse(Long id);
}