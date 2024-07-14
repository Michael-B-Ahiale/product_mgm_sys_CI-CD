package com.abmike.ecommerce_prod_mgmt.service;

import com.abmike.ecommerce_prod_mgmt.model.Category;
import com.abmike.ecommerce_prod_mgmt.repository.CategoryRepository;
import com.abmike.ecommerce_prod_mgmt.util.BinaryTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        updateBinaryTree();
        return savedCategory;
    }

    public Optional<Category> updateCategory(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    category.setId(id);
                    Category updatedCategory = categoryRepository.save(category);
                    updateBinaryTree();
                    return updatedCategory;
                });
    }

    public boolean deleteCategory(Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    updateBinaryTree();
                    return true;
                })
                .orElse(false);
    }

    private void updateBinaryTree() {
        List<Category> allCategories = categoryRepository.findAll();
        if (!allCategories.isEmpty()) {
            Category root = allCategories.get(0);
            for (int i = 1; i < allCategories.size(); i++) {
                BinaryTreeUtil.insert(root, allCategories.get(i));
            }
        }
    }

    public Category searchCategory(String categoryName) {
        List<Category> allCategories = categoryRepository.findAll();
        if (!allCategories.isEmpty()) {
            Category root = allCategories.get(0);
            return BinaryTreeUtil.search(root, categoryName);
        }
        return null;
    }
}