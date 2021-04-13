package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    Comment createComment(Comment comment);
    void deleteComment(String articleId,String commentId);
    Comment getCommentById(String commentId);


}
