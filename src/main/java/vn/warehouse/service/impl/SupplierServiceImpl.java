package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.warehouse.dto.request.SupplierRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.SupplierResponse;
import vn.warehouse.exception.ResourceNotFoundException;
import vn.warehouse.model.Supplier;
import vn.warehouse.model.Warehouse;
import vn.warehouse.repository.SupplierRepository;
import vn.warehouse.repository.WarehouseRepository;
import vn.warehouse.service.SupplierService;
import vn.warehouse.util.PaginationUtil;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public SupplierResponse createSupplier(SupplierRequest request) {
        log.info("Creating supplier with name: {}", request.getName());
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với id: " + request.getWarehouseId()));
        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .address(request.getAddress())
                .contactNumber(request.getContactNumber())
                .warehouse(warehouse)
                .build();
        supplier = supplierRepository.save(supplier);
        return mapToResponse(supplier);
    }

    @Override
    public SupplierResponse getSupplierById(Long id) {
        log.info("Fetching supplier with id: {}", id);
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + id));
        return mapToResponse(supplier);
    }

    @Override
    public PageResponse<SupplierResponse> getAllSuppliers(int page, int size, String sort, String direction) {
        log.info("Fetching suppliers with page: {}, size: {}, sort: {}, direction: {}", page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Supplier> supplierPage = supplierRepository.findAll(pageable);
        return PageResponse.<SupplierResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(supplierPage.getTotalElements())
                .totalPages(supplierPage.getTotalPages())
                .content(supplierPage.getContent().stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        log.info("Updating supplier with id: {}", id);
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + id));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với id: " + request.getWarehouseId()));
        supplier.setName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setContactNumber(request.getContactNumber());
        supplier.setWarehouse(warehouse);
        supplier = supplierRepository.save(supplier);
        return mapToResponse(supplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        log.info("Deleting supplier with id: {}", id);
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + id);
        }
        supplierRepository.deleteById(id);
    }

    private SupplierResponse mapToResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setName(supplier.getName());
        response.setAddress(supplier.getAddress());
        response.setContactNumber(supplier.getContactNumber());
        response.setWarehouseId(supplier.getWarehouse().getId());
        response.setProductSupplierIds(supplier.getProductSuppliers().stream()
                .map(productSupplier -> productSupplier.getId())
                .collect(Collectors.toList()));
        response.setTransactionIds(supplier.getTransactions().stream()
                .map(transaction -> transaction.getId())
                .collect(Collectors.toList()));
        return response;
    }
}