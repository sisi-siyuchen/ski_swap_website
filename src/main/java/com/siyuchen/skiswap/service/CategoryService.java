package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.exception.CategoryNotFoundException;
import com.siyuchen.skiswap.model.Category;

import java.util.List;

/**
 * @author Siyu Chen
 */
public interface CategoryService {
    public void saveCategory(Category category);
    Category findCategoryByName(String name) throws CategoryNotFoundException;
    public List<Category> getAllCategories();
    public Category getCategoriesByProductId(int id);
}
