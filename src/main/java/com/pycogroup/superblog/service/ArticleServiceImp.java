package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.repository.ArticleRepository;
import com.pycogroup.superblog.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService {
	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}

	@Override
	public Article createArticle(Article article) {
		return articleRepository.save(article);
	}

	@Override
	public Article findArticleById(ObjectId id) {
		return articleRepository.findArticleById(id);
	}

	@Override
	public Article categorizeArticle(Article article, String categoryName) {
		if (articleRepository.findById(article.getId()) != null ){
			article.setCategory(categoryRepository.findByName(categoryName));
			article.getAuthor();
			article.getContent();
			article.getId();
			article.getTitle();
			articleRepository.save(article);
			return article;
		}
		else return null;
	}

	@Override
	public Article categorizeOldArticle(Article article, String categoryName) {
		if(article != null){
			article.setCategory(categoryRepository.findByName(categoryName));
			articleRepository.save(article);
		}
		return article;
	}

	/*@Override
	public void deleteArticle(String articleId) {
		Article article = articleRepository.findByArticleId(new ObjectId(articleId));
		articleRepository.deleteById(new ObjectId(articleId));
		commentRepository.deleteAllByArticle_Id(articleId);
	}*/
}
