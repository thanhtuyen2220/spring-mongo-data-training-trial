package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.api.ArticlesApi;
import com.pycogroup.superblog.api.model.ArticleListResponse;
import com.pycogroup.superblog.api.model.ObjectCreationSuccessResponse;
import com.pycogroup.superblog.api.model.CreateArticleRequest;
import com.pycogroup.superblog.api.model.UpdateCategoryArticleRequest;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.service.ArticleService;
import com.pycogroup.superblog.service.CategoryService;
import com.pycogroup.superblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ArticleController implements ArticlesApi {
	@Autowired
	private ArticleService articleService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<ArticleListResponse> getArticleList() {
		List<Article> articleList = articleService.getAllArticles();
		return buildArticleListResponse(articleList);
	}

	@Override
	public ResponseEntity<ObjectCreationSuccessResponse> updateCategoryArticle(@Valid UpdateCategoryArticleRequest updateCategoryArticleRequest) {
		Category category = categoryService.findByName((updateCategoryArticleRequest.getCategoryName()));
		Article article = articleService.findArticleById(new ObjectId(updateCategoryArticleRequest.getArticleId()));
		//Article article = modelMapper.map(updateCategoryArticleRequest,Article.class);
		Article persistArticle = articleService.categorizeOldArticle(article,category.getName());
		ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
		result.setId(persistArticle.getId().toString());
		result.setResponseCode(HttpStatus.CREATED.value());
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ObjectCreationSuccessResponse> createArticle(@Valid CreateArticleRequest createArticleRequest) {
		User user = userService.findUserById(new ObjectId(createArticleRequest.getAuthorId()));
		Category category = categoryService.findByName(createArticleRequest.getCategoryId());
		Article article = modelMapper.map(createArticleRequest, Article.class);
		article.setAuthor(user);
		article.setCategory(category);
		Article persistArticle = articleService.createArticle(article);
		ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
		result.setId(persistArticle.getId().toString());
		result.setResponseCode(HttpStatus.CREATED.value());
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	private ResponseEntity<ArticleListResponse> buildArticleListResponse(List<Article> articleList) {
		ArticleListResponse articleListResponse = new ArticleListResponse();

		if (articleList != null) {
			articleList.forEach(item -> articleListResponse.addArticlesItem(modelMapper.map(item, com.pycogroup.superblog.api.model.ArticleResponseModel.class)));
		}
		return new ResponseEntity(articleListResponse, HttpStatus.OK);
	}

}
