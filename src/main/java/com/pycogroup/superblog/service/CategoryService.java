package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(Category category);
    Category findByName(String categoryName);
}
