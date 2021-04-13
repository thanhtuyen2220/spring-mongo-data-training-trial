package com.pycogroup.superblog.repository;

import com.pycogroup.superblog.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, ObjectId>{
    Category findByName(String categoryName);
}
