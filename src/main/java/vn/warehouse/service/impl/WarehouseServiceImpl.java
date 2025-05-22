package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.warehouse.dto.request.WarehouseRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.WarehouseResponse;
import vn.warehouse.model.Warehouse;
import vn.warehouse.repository.WarehouseRepository;
import vn.warehouse.service.WarehouseService;
import vn.warehouse.util.PaginationUtil;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Override
    public WarehouseResponse createWarehouse(WarehouseRequest request) {
        log.info("Creating warehouse with name: {}", request.getName());
        Warehouse warehouse = Warehouse.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();
        warehouse = warehouseRepository.save(warehouse);
        return mapToResponse(warehouse);
    }

    @Override
    public WarehouseResponse getWarehouseById(Long id) {
        log.info("Fetching warehouse with id: {}", id);
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy kho hàng với id: " + id));
        return mapToResponse(warehouse);
    }

    @Override
    public PageResponse<WarehouseResponse> getAllWarehouses(int page, int size, String sort, String direction) {
        log.info("Fetching warehouses with page: {}, size: {}, sort: {}, direction: {}", page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Warehouse> warehousePage = warehouseRepository.findAll(pageable);
        return PageResponse.<WarehouseResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(warehousePage.getTotalElements())
                .totalPages(warehousePage.getTotalPages())
                .content(warehousePage.getContent().stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public WarehouseResponse updateWarehouse(Long id, WarehouseRequest request) {
        log.info("Updating warehouse with id: {}", id);
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy kho hàng với id: " + id));
        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());
        warehouse = warehouseRepository.save(warehouse);
        return mapToResponse(warehouse);
    }

    @Override
    public void deleteWarehouse(Long id) {
        log.info("Deleting warehouse with id: {}", id);
        if (!warehouseRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy kho hàng với id: " + id);
        }
        warehouseRepository.deleteById(id);
    }

    private WarehouseResponse mapToResponse(Warehouse warehouse) {
        WarehouseResponse response = new WarehouseResponse();
        response.setId(warehouse.getId());
        response.setName(warehouse.getName());
        response.setLocation(warehouse.getLocation());
        response.setSupplierIds(warehouse.getSuppliers().stream()
                .map(supplier -> supplier.getId())
                .collect(Collectors.toList()));
        response.setInventoryIds(warehouse.getInventory().stream()
                .map(inventory -> inventory.getId())
                .collect(Collectors.toList()));
        return response;
    }
}