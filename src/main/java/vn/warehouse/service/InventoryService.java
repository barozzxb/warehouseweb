package vn.warehouse.service;

import vn.warehouse.dto.request.InventoryRequest;
import vn.warehouse.dto.response.InventoryResponse;
import vn.warehouse.dto.response.PageResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest request);
    InventoryResponse getInventoryById(Long id);
    List<InventoryResponse> getAllInventories();
    InventoryResponse updateInventory(Long id, InventoryRequest request);
    void deleteInventory(Long id);

    PageResponse<InventoryResponse> getInventoriesByWarehouse(Long id, int page, int size, String sort, String direction);
}