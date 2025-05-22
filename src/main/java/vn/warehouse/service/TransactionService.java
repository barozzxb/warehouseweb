package vn.warehouse.service;

import vn.warehouse.dto.request.TransactionRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.TransactionResponse;
import vn.warehouse.model.enumuration.TransactionType;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);

    TransactionResponse getTransactionById(Long id);

    PageResponse<TransactionResponse> getAllTransactions(int page, int size, String sort, String direction, TransactionType type, String status);

    TransactionResponse updateTransaction(Long id, TransactionRequest request);

    void deleteTransaction(Long id);

    TransactionResponse createImportTransaction(TransactionRequest request);

    TransactionResponse createExportTransaction(TransactionRequest request);

    PageResponse<TransactionResponse> getUserTransactions(Long id, int page, int size, String sort, String direction);
}
