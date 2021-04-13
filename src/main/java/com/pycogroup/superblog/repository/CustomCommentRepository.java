package com.pycogroup.superblog.repository;

public interface CustomCommentRepository {
    void deleteCommentByCommentId(String articleId, String commentId);
}
