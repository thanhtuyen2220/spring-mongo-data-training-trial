package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Article;
import org.bson.types.ObjectId;

import java.util.List;

public interface ArticleService {
	List<Article> getAllArticles();
	Article createArticle(Article article);
	Article findArticleById(ObjectId id);
	Article categorizeArticle(Article article, String categoryName);
	Article categorizeOldArticle(Article article,String categoryName);
	void deleteArticle(String articleId);
}
