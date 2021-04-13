package com.pycogroup.superblog.repository;

import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
    Comment findCommentById(ObjectId id);
    Comment deleteAllByArticleId(String articleId);
}
