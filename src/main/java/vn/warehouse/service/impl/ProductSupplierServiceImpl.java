package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.warehouse.dto.request.ProductSupplierRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.ProductSupplierResponse;
import vn.warehouse.exception.ResourceNotFoundException;
import vn.warehouse.model.Product;
import vn.warehouse.model.ProductSupplier;
import vn.warehouse.model.Supplier;
import vn.warehouse.repository.ProductRepository;
import vn.warehouse.repository.ProductSupplierRepository;
import vn.warehouse.repository.SupplierRepository;
import vn.warehouse.service.ProductSupplierService;
import vn.warehouse.util.PaginationUtil;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSupplierServiceImpl implements ProductSupplierService {
    private final ProductSupplierRepository productSupplierRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public ProductSupplierResponse createProductSupplier(ProductSupplierRequest request) {
        log.info("Creating product-supplier for product id: {}, supplier id: {}", request.getProductId(), request.getSupplierId());
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + request.getProductId()));
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + request.getSupplierId()));
        ProductSupplier productSupplier = ProductSupplier.builder()
                .product(product)
                .supplier(supplier)
                .supplyDate(request.getSupplyDate())
                .supplyPrice(request.getSupplyPrice())
                .supplyQuantity(request.getSupplyQuantity())
                .build();
        productSupplier = productSupplierRepository.save(productSupplier);
        return mapToResponse(productSupplier);
    }

    @Override
    public ProductSupplierResponse getProductSupplierById(Long id) {
        log.info("Fetching product-supplier with id: {}", id);
        ProductSupplier productSupplier = productSupplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy quan hệ sản phẩm-nhà cung cấp với id: " + id));
        return mapToResponse(productSupplier);
    }

    @Override
    public PageResponse<ProductSupplierResponse> getAllProductSuppliers(int page, int size, String sort, String direction) {
        log.info("Fetching product-suppliers with page: {}, size: {}, sort: {}, direction: {}", page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<ProductSupplier> productSupplierPage = productSupplierRepository.findAll(pageable);
        return PageResponse.<ProductSupplierResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(productSupplierPage.getTotalElements())
                .totalPages(productSupplierPage.getTotalPages())
                .content(productSupplierPage.getContent().stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ProductSupplierResponse updateProductSupplier(Long id, ProductSupplierRequest request) {
        log.info("Updating product-supplier with id: {}", id);
        ProductSupplier productSupplier = productSupplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy quan hệ sản phẩm-nhà cung cấp với id: " + id));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + request.getProductId()));
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + request.getSupplierId()));
        productSupplier.setProduct(product);
        productSupplier.setSupplier(supplier);
        productSupplier.setSupplyDate(request.getSupplyDate());
        productSupplier.setSupplyPrice(request.getSupplyPrice());
        productSupplier.setSupplyQuantity(request.getSupplyQuantity());
        productSupplier = productSupplierRepository.save(productSupplier);
        return mapToResponse(productSupplier);
    }

    @Override
    public void deleteProductSupplier(Long id) {
        log.info("Deleting product-supplier with id: {}", id);
        if (!productSupplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy quan hệ sản phẩm-nhà cung cấp với id: " + id);
        }
        productSupplierRepository.deleteById(id);
    }

    private ProductSupplierResponse mapToResponse(ProductSupplier productSupplier) {
        ProductSupplierResponse response = new ProductSupplierResponse();
        response.setId(productSupplier.getId());
        response.setProductId(productSupplier.getProduct().getId());
        response.setSupplierId(productSupplier.getSupplier().getId());
        response.setSupplyDate(productSupplier.getSupplyDate());
        response.setSupplyPrice(productSupplier.getSupplyPrice());
        response.setSupplyQuantity(productSupplier.getSupplyQuantity());
        return response;
    }
}