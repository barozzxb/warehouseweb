package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.warehouse.dto.request.ProductRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.ProductResponse;
import vn.warehouse.exception.ConflictException;
import vn.warehouse.exception.ResourceNotFoundException;
import vn.warehouse.model.Category;
import vn.warehouse.model.Inventory;
import vn.warehouse.model.Product;
import vn.warehouse.repository.CategoryRepository;
import vn.warehouse.repository.InventoryRepository;
import vn.warehouse.repository.ProductRepository;
import vn.warehouse.service.ProductService;
import vn.warehouse.util.PaginationUtil;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating product with name: {}", request.getName());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với id: " + request.getCategoryId()));
        Inventory inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho với id: " + request.getInventoryId()));
        Product product = Product.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .location(request.getLocation())
                .entryDate(request.getEntryDate())
                .expiryDate(request.getExpiryDate())
                .category(category)
                .inventory(inventory)
                .build();
        product = productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + id));
        return mapToResponse(product);
    }

    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, int size, String sort, String direction) {
        log.info("Fetching all products");
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Product> productPage = productRepository.findAll(pageable);

        return PageResponse.<ProductResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .content(productPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("Updating product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + id));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với id: " + request.getCategoryId()));
        Inventory inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho với id: " + request.getInventoryId()));
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());
        product.setUnitPrice(request.getUnitPrice());
        product.setLocation(request.getLocation());
        product.setEntryDate(request.getEntryDate());
        product.setExpiryDate(request.getExpiryDate());
        product.setCategory(category);
        product.setInventory(inventory);
        product = productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + id));
        if (!product.getTransactions().isEmpty()) {
            throw new ConflictException("Sản phẩm còn giao dịch liên quan, không thể xóa");
        }
        productRepository.deleteById(id);
    }

    @Override
    public PageResponse<ProductResponse> getProductsByCategory(Long categoryId, int page, int size, String sort, String direction) {
        log.info("Fetching products for category id: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Không tìm thấy danh mục với id: " + categoryId);
        }
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);

        return PageResponse.<ProductResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .content(productPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    @Override
    public PageResponse<ProductResponse> getProductsByInventory(Long inventoryId, int page, int size, String sort, String direction) {
        log.info("Fetching products for inventory id: {}", inventoryId);
        if (!inventoryRepository.existsById(inventoryId)) {
            throw new ResourceNotFoundException("Không tìm thấy kho với id: " + inventoryId);
        }
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Product> productPage = productRepository.findByInventoryId(inventoryId, pageable);

        return PageResponse.<ProductResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .content(productPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    @Override
    public PageResponse<ProductResponse> getExpiringProducts(int page, int size, String sort, String direction, int days) {
        log.info("Fetching expiring products within {} days", days);
        LocalDateTime expiryThreshold = LocalDateTime.now().plusDays(days);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Product> productPage = productRepository.findByExpiryDateBefore(expiryThreshold, pageable);

        return PageResponse.<ProductResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .content(productPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setQuantity(product.getQuantity());
        response.setUnitPrice(product.getUnitPrice());
        response.setLocation(product.getLocation());
        response.setEntryDate(product.getEntryDate());
        response.setExpiryDate(product.getExpiryDate());
        response.setCategoryId(product.getCategory().getId());
        response.setInventoryId(product.getInventory().getId());
        response.setProductSupplierIds(product.getProductSuppliers().stream()
                .map(productSupplier -> productSupplier.getId())
                .collect(Collectors.toList()));
        response.setTransactionIds(product.getTransactions().stream()
                .map(transaction -> transaction.getId())
                .collect(Collectors.toList()));
        return response;
    }
}