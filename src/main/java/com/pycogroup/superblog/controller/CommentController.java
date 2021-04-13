package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.api.CommentsApi;
import com.pycogroup.superblog.api.model.CreateCommentRequest;
import com.pycogroup.superblog.api.model.ObjectCreationSuccessResponse;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Comment;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.service.ArticleService;
import com.pycogroup.superblog.service.CommentService;
import com.pycogroup.superblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class CommentController implements CommentsApi {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<ObjectCreationSuccessResponse> createComment(@Valid CreateCommentRequest createCommentRequest) {
        Article article = articleService.findArticleById(new ObjectId(createCommentRequest.getArticleId()));
        User user = userService.findUserById(new ObjectId(createCommentRequest.getUserAuthorId()));
        Comment comment = modelMapper.map(createCommentRequest, Comment.class);
        comment.setArticle(article);
        comment.setUser(user);
        Comment persistComment = commentService.createComment(comment);
        ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
        result.setId(persistComment.getId().toString());
        result.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
