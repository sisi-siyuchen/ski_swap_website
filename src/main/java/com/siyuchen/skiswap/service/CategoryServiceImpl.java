package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.exception.CategoryNotFoundException;
import com.siyuchen.skiswap.model.Category;
import com.siyuchen.skiswap.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Siyu Chen
 *  This class is to handle the business logic regarding category model.
 */
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Save the category into database
     * @param category
     */
    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Find the category from database based on the name of category.
     * @param name
     * @return
     * @throws CategoryNotFoundException
     */
    @Override
    public Category findCategoryByName(String name) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findCategoryByName(name);
        Category category = optionalCategory.orElseThrow(() -> new CategoryNotFoundException("Category not exist"));
        return category;
    }

    /**
     * Get all the categories stored in the database.
     * @return
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Find the category associated with given product's ID.
     * @param id
     * @return
     */
    @Override
    public Category getCategoriesByProductId(int id) {
        return categoryRepository.findCategoryByProductId(id);
    }


}
