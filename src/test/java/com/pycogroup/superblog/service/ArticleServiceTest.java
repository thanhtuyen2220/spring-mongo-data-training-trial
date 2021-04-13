package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.repository.ArticleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ArticleServiceTest {
	private static final int INIT_ARTICLE_NUMBER = 5;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ArticleService articleService;
	private ArrayList<String> TEST_ID = new ArrayList<>();
	@Before
	public void init() {
		mongoTemplate.remove(Article.class).all();
		mongoTemplate.remove(User.class).all();
		User author = mongoTemplate.save(User.builder()
			.name(RandomStringUtils.random(40))
			.email("fake"+RandomStringUtils.randomAlphabetic(5)+ "@.local")
			.build());
		for (int i = 0; i < INIT_ARTICLE_NUMBER; i++) {
			Article article = Article.builder()
				.content(RandomStringUtils.random(40))
				.title(RandomStringUtils.random(500))
				.author(author)
				.build();
			mongoTemplate.save(article);
			TEST_ID.add(article.getId().toString());
		}
	}

	@Test
	public void testFindAllMustReturnEnoughQuantity() {
		List<Article> articleList = articleService.getAllArticles();
		Assert.assertEquals(INIT_ARTICLE_NUMBER, articleList.size());
	}
	@Test
	public void testCreateArticleGivenArticleWhenSuccessThenReturnArticle() {
		mongoTemplate.remove(Article.class).all();
		mongoTemplate.remove(User.class).all();
		mongoTemplate.remove(Category.class).all();
		User author = mongoTemplate.save(User.builder()
				.name(RandomStringUtils.random(40))
				.email("fake"+RandomStringUtils.randomAlphabetic(5)+ "@.local")
				.build());
		Category category = mongoTemplate.save(Category.builder().name("Book").build());
		Article article = articleService.createArticle(Article.builder()
				.title("Article 1")
				.content("Lorem ..")
				.author(author)
				.category(category)
				.build());
		Assert.assertEquals("Article 1", article.getTitle());
		Assert.assertEquals(author.getId().toString(), article.getAuthor().getId().toString());
	}
}
