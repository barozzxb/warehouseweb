package vn.warehouse.service;

import vn.warehouse.dto.request.CategoryRequest;
import vn.warehouse.dto.response.CategoryResponse;
import vn.warehouse.dto.response.PageResponse;


public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    PageResponse<CategoryResponse> getAllCategories(int page, int size, String sort, String direction, String name);
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}
