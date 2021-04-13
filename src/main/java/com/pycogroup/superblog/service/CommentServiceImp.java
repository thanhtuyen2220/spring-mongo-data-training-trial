package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Comment;
import com.pycogroup.superblog.repository.ArticleRepository;
import com.pycogroup.superblog.repository.CommentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String articleId, String commentId) {
        Article article = getArticleById(articleId);
        Comment comment = getCommentById(commentId);
        if(comment.getArticle().getId().equals(articleId)){
            commentRepository.deleteCommentByCommentId(articleId,commentId);
        }
        commentRepository.deleteById(new ObjectId(commentId));
    }

    @Override
    public Comment getCommentById(String commentId) {
        return commentRepository.findByCommentId(new ObjectId(commentId));
    }
    public Article getArticleById(String articleId){
        return articleRepository.findByArticleId(new ObjectId(articleId));
    }

}
