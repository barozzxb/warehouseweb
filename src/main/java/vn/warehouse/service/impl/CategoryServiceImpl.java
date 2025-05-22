package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.warehouse.dto.request.CategoryRequest;
import vn.warehouse.dto.response.CategoryResponse;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.exception.ConflictException;
import vn.warehouse.exception.ResourceNotFoundException;
import vn.warehouse.model.Category;
import vn.warehouse.repository.CategoryRepository;
import vn.warehouse.service.CategoryService;
import vn.warehouse.util.PaginationUtil;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("Creating category with name: {}", request.getName());
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        category = categoryRepository.save(category);
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        log.info("Fetching category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với id: " + id));
        return mapToResponse(category);
    }

    @Override
    public PageResponse<CategoryResponse> getAllCategories(int page, int size, String sort, String direction, String name) {
        log.info("Fetching all categories with name filter: {}", name);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Category> categoryPage = name == null || name.isEmpty()
                ? categoryRepository.findAll(pageable)
                : categoryRepository.findByNameContainingIgnoreCase(name, pageable);

        return PageResponse.<CategoryResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .content(categoryPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        log.info("Updating category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với id: " + id));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category = categoryRepository.save(category);
        return mapToResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với id: " + id));
        if (!category.getProducts().isEmpty()) {
            throw new ConflictException("Danh mục còn sản phẩm liên quan, không thể xóa");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setProductIds(category.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList()));
        return response;
    }
}