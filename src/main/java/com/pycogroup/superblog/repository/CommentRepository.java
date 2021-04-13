package com.pycogroup.superblog.repository;

import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentRepository extends MongoRepository<Comment, ObjectId>,CustomCommentRepository,
        QuerydslPredicateExecutor<Comment> {
    Comment findCommentById(ObjectId id);
    Comment deleteAllByArticleId(String articleId);
    Comment findByCommentId(ObjectId id);
}
