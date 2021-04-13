package com.pycogroup.superblog.repository;

import com.mongodb.BasicDBObject;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    @Autowired
    MongoOperations mongoOperations;
    @Override
    public void deleteCommentByCommentId(String articleId, String commentId) {
        Query query = queryWithArticleIdAndCommentId(articleId, commentId);
        Update update = new Update().pull("comments", new BasicDBObject("commentId", commentId));
        mongoOperations.updateFirst(query, update, Comment.class);
    }
    private Query queryWithArticleIdAndCommentId(String articleId, String commentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("articleId").is(articleId));
        query.addCriteria(Criteria.where("comments.commentId").is(commentId));
        return query;
    }
}
