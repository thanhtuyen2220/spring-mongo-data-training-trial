package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.api.CategoriesApi;
import com.pycogroup.superblog.api.model.CategoryListResponse;
import com.pycogroup.superblog.api.model.CategoryResponseModel;
import com.pycogroup.superblog.api.model.CreateCategoryRequest;
import com.pycogroup.superblog.api.model.ObjectCreationSuccessResponse;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class CategoryController implements CategoriesApi {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<ObjectCreationSuccessResponse> createCategory(@Valid CreateCategoryRequest createCategoryRequest) {
        Category category = modelMapper.map(createCategoryRequest, Category.class);
        Category persistCategory = categoryService.createCategory(category);
        ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
        result.setId(persistCategory.getId().toString());
        result.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CategoryListResponse> getCategoryList() {
        List<Category> categoryList = categoryService.getAllCategories();
        return buildCategoryListResponse(categoryList);
    }

    private ResponseEntity<CategoryListResponse> buildCategoryListResponse(List<Category> categoryList) {
        CategoryListResponse categoryListResponse = new CategoryListResponse();
        if(categoryList != null) {
            categoryList.forEach(item -> categoryListResponse.addCategoriesItem(modelMapper.map(item, CategoryResponseModel.class)));
        }
        return new ResponseEntity<>(categoryListResponse, HttpStatus.OK);
    }
}
