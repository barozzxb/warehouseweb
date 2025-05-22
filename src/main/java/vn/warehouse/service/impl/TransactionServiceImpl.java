package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.warehouse.dto.request.TransactionRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.TransactionResponse;
import vn.warehouse.exception.ConflictException;
import vn.warehouse.exception.ResourceNotFoundException;
import vn.warehouse.model.Product;
import vn.warehouse.model.Supplier;
import vn.warehouse.model.Transaction;
import vn.warehouse.model.User;
import vn.warehouse.model.enumuration.TransactionType;
import vn.warehouse.repository.ProductRepository;
import vn.warehouse.repository.SupplierRepository;
import vn.warehouse.repository.TransactionRepository;
import vn.warehouse.repository.UserRepository;
import vn.warehouse.service.TransactionService;
import vn.warehouse.util.PaginationUtil;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        log.info("Creating transaction for product id: {}, employee id: {}", request.getProductId(), request.getEmployeeId());
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + request.getProductId()));
        User employee = userRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với id: " + request.getEmployeeId()));
        Supplier supplier = null;
        if (request.getSupplierId() != null) {
            supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + request.getSupplierId()));
        }
        Transaction transaction = Transaction.builder()
                .quantity(request.getQuantity())
                .type(request.getType())
                .status(request.getStatus())
                .transactionDate(request.getTransactionDate())
                .product(product)
                .employee(employee)
                .supplier(supplier)
                .build();
        transaction = transactionRepository.save(transaction);
        return mapToResponse(transaction);
    }

    @Override
    public TransactionResponse getTransactionById(Long id) {
        log.info("Fetching transaction with id: {}", id);
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giao dịch với id: " + id));
        return mapToResponse(transaction);
    }

    @Override
    public PageResponse<TransactionResponse> getAllTransactions(int page, int size, String sort, String direction, TransactionType type, String status) {
        log.info("Fetching transactions with page: {}, size: {}, type: {}, status: {}", page, size, type, status);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Transaction> transactionPage;
        if (type != null && status != null && !status.isEmpty()) {
            transactionPage = transactionRepository.findByTypeAndStatus(type, status, pageable);
        } else if (type != null) {
            transactionPage = transactionRepository.findByType(type, pageable);
        } else if (status != null && !status.isEmpty()) {
            transactionPage = transactionRepository.findByStatus(status, pageable);
        } else {
            transactionPage = transactionRepository.findAll(pageable);
        }

        return PageResponse.<TransactionResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .content(transactionPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    @Override
    @Transactional
    public TransactionResponse updateTransaction(Long id, TransactionRequest request) {
        log.info("Updating transaction with id: {}", id);
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giao dịch với id: " + id));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + request.getProductId()));
        User employee = userRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với id: " + request.getEmployeeId()));
        Supplier supplier = null;
        if (request.getSupplierId() != null) {
            supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + request.getSupplierId()));
        }

        // Kiểm tra ảnh hưởng đến tồn kho nếu thay đổi quantity hoặc type
        if (transaction.getType() != request.getType() || !transaction.getQuantity().equals(request.getQuantity())) {
            validateInventoryImpact(transaction, request);
        }

        transaction.setQuantity(request.getQuantity());
        transaction.setType(request.getType());
        transaction.setStatus(request.getStatus());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setProduct(product);
        transaction.setEmployee(employee);
        transaction.setSupplier(supplier);
        transaction = transactionRepository.save(transaction);
        return mapToResponse(transaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long id) {
        log.info("Deleting transaction with id: {}", id);
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giao dịch với id: " + id));

        // Kiểm tra ảnh hưởng đến tồn kho
        Product product = transaction.getProduct();
        if (transaction.getType() == TransactionType.IMPORT) {
            if (product.getQuantity() < transaction.getQuantity()) {
                throw new ConflictException("Không thể xóa giao dịch nhập hàng vì số lượng tồn kho không đủ");
            }
            product.setQuantity(product.getQuantity() - transaction.getQuantity());
        } else if (transaction.getType() == TransactionType.EXPORT) {
            product.setQuantity(product.getQuantity() + transaction.getQuantity());
        }
        productRepository.save(product);

        transactionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TransactionResponse createImportTransaction(TransactionRequest request) {
        log.info("Creating import transaction for product id: {}", request.getProductId());
        if (request.getType() != TransactionType.IMPORT) {
            throw new ConflictException("Loại giao dịch phải là IMPORT");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + request.getProductId()));
        User employee = userRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với id: " + request.getEmployeeId()));
        Supplier supplier = null;
        if (request.getSupplierId() != null) {
            supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + request.getSupplierId()));
        }

        // Cập nhật số lượng tồn kho
        product.setQuantity(product.getQuantity() + request.getQuantity());
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .quantity(request.getQuantity())
                .type(TransactionType.IMPORT)
                .status("COMPLETED")
                .transactionDate(request.getTransactionDate())
                .product(product)
                .employee(employee)
                .supplier(supplier)
                .build();
        transaction = transactionRepository.save(transaction);
        return mapToResponse(transaction);
    }

    @Override
    @Transactional
    public TransactionResponse createExportTransaction(TransactionRequest request) {
        log.info("Creating export transaction for product id: {}", request.getProductId());
        if (request.getType() != TransactionType.EXPORT) {
            throw new ConflictException("Loại giao dịch phải là EXPORT");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + request.getProductId()));
        User employee = userRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với id: " + request.getEmployeeId()));
        Supplier supplier = null;
        if (request.getSupplierId() != null) {
            supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với id: " + request.getSupplierId()));
        }

        // Kiểm tra và cập nhật số lượng tồn kho
        if (product.getQuantity() < request.getQuantity()) {
            throw new ConflictException("Không đủ hàng tồn kho để xuất");
        }
        product.setQuantity(product.getQuantity() - request.getQuantity());
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .quantity(request.getQuantity())
                .type(TransactionType.EXPORT)
                .status("COMPLETED")
                .transactionDate(request.getTransactionDate())
                .product(product)
                .employee(employee)
                .supplier(supplier)
                .build();
        transaction = transactionRepository.save(transaction);
        return mapToResponse(transaction);
    }

    private void validateInventoryImpact(Transaction transaction, TransactionRequest request) {
        Product product = transaction.getProduct();
        int currentQuantity = product.getQuantity();
        int transactionQuantity = transaction.getQuantity();
        int newQuantity = request.getQuantity();
        TransactionType currentType = transaction.getType();
        TransactionType newType = request.getType();

        if (currentType == TransactionType.IMPORT) {
            currentQuantity -= transactionQuantity;
        } else if (currentType == TransactionType.EXPORT) {
            currentQuantity += transactionQuantity;
        }

        if (newType == TransactionType.IMPORT) {
            currentQuantity += newQuantity;
        } else if (newType == TransactionType.EXPORT) {
            if (currentQuantity < newQuantity) {
                throw new ConflictException("Không đủ hàng tồn kho để cập nhật giao dịch xuất");
            }
            currentQuantity -= newQuantity;
        }

        product.setQuantity(currentQuantity);
        productRepository.save(product);
    }

    @Override
    public PageResponse<TransactionResponse> getUserTransactions(Long id, int page, int size, String sort, String direction) {
        log.info("Fetching transactions for user id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id);
        }
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<Transaction> transactionPage = transactionRepository.findByEmployeeId(id, pageable);

        return PageResponse.<TransactionResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .content(transactionPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setQuantity(transaction.getQuantity());
        response.setType(transaction.getType());
        response.setStatus(transaction.getStatus());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setProductId(transaction.getProduct().getId());
        response.setEmployeeId(transaction.getEmployee().getId());
        response.setSupplierId(transaction.getSupplier() != null ? transaction.getSupplier().getId() : null);
        return response;
    }
}