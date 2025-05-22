package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.warehouse.dto.request.InventoryRequest;
import vn.warehouse.dto.response.InventoryResponse;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.exception.ResourceNotFoundException;
import vn.warehouse.model.Inventory;
import vn.warehouse.model.Warehouse;
import vn.warehouse.repository.InventoryRepository;
import vn.warehouse.repository.WarehouseRepository;
import vn.warehouse.service.InventoryService;
import vn.warehouse.util.PaginationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {
        log.info("Creating inventory for warehouse id: {}", request.getWarehouseId());
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với id: " + request.getWarehouseId()));
        Inventory inventory = Inventory.builder()
                .warehouse(warehouse)
                .build();
        inventory = inventoryRepository.save(inventory);
        return mapToResponse(inventory);
    }

    @Override
    public InventoryResponse getInventoryById(Long id) {
        log.info("Fetching inventory with id: {}", id);
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho với id: " + id));
        return mapToResponse(inventory);
    }

    @Override
    public List<InventoryResponse> getAllInventories() {
        log.info("Fetching all inventories");
        return inventoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponse updateInventory(Long id, InventoryRequest request) {
        log.info("Updating inventory with id: {}", id);
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho với id: " + id));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với id: " + request.getWarehouseId()));
        inventory.setWarehouse(warehouse);
        inventory = inventoryRepository.save(inventory);
        return mapToResponse(inventory);
    }

    @Override
    public void deleteInventory(Long id) {
        log.info("Deleting inventory with id: {}", id);
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy kho với id: " + id);
        }
        inventoryRepository.deleteById(id);
    }

    @Override
    public PageResponse<InventoryResponse> getInventoriesByWarehouse(Long warehouseId, int page, int size, String sort, String direction) {
        log.info("Fetching inventories for warehouse id: {}", warehouseId);
        if (!warehouseRepository.existsById(warehouseId)) {
            throw new ResourceNotFoundException("Không tìm thấy kho hàng với id: " + warehouseId);
        }
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Inventory> inventoryPage = inventoryRepository.findByWarehouseId(warehouseId, pageable);

        return PageResponse.<InventoryResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(inventoryPage.getTotalElements())
                .totalPages(inventoryPage.getTotalPages())
                .content(inventoryPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        InventoryResponse response = new InventoryResponse();
        response.setId(inventory.getId());
        response.setWarehouseId(inventory.getWarehouse().getId());
        response.setProductIds(inventory.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList()));
        return response;
    }
}