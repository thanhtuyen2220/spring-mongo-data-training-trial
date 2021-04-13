package com.pycogroup.superblog.repository;

import com.pycogroup.superblog.model.Article;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, ObjectId> {
    //Article findByArticleId(ObjectId id);
    Article findArticleById(ObjectId id);
<<<<<<< HEAD
    Article deleteArticleById(ObjectId id);
    Article findByArticleId(ObjectId id);
=======
>>>>>>> parent of 9198cee (before impl comment delete func)
}
