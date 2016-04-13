package com.todayz.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.todayz.controller.support.ItemDto;
import com.todayz.domain.club.Menu;
import com.todayz.domain.common.Image;
import com.todayz.domain.item.Article;
import com.todayz.service.ArticleService;
import com.todayz.service.ImageService;
import com.todayz.service.ItemService;
import com.todayz.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ImageService imageService;

	@Autowired
	private ItemService<Article> itemService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Article createArticle(ItemDto.Create create, MultipartFile articleImage, Long menuId) throws IOException {
		if (articleImage != null) {
			Image image = null;
			image = imageService.uploadImage(articleImage);
			create.setArticleImage(image);
		}

		Article newArticle = modelMapper.map(create, Article.class);
		Menu parent = menuService.getMenu(menuId);
		newArticle.setParent(parent);
		newArticle = itemService.create(newArticle);// create(create);

		log.info("article create success. {}", newArticle.getTitle());
		return newArticle;
	}

	@Override
	public Article updateArticle(ItemDto.Update updateDto, MultipartFile articleImage, Long id) throws IOException {
		if (articleImage != null) {
			Image image = null;
			image = imageService.uploadImage(articleImage);
			updateDto.setArticleImage(image);
		}

		Article updatedArticle = modelMapper.map(updateDto, Article.class);
		Article article = itemService.getItem(id);

		article.setTitle(updatedArticle.getTitle());
		article.setContent(updatedArticle.getContent());
		if (updatedArticle.getArticleImage() != null) {
			article.setArticleImage(updatedArticle.getArticleImage());
		}

		article.setUpdatedDate(new Date());
		log.info("article update success. {}", article.getTitle());
		return article;
	}
}
