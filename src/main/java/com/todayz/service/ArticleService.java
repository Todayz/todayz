package com.todayz.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.todayz.controller.support.ItemDto;
import com.todayz.domain.item.Article;

public interface ArticleService {
	public Article createArticle(ItemDto.Create create, MultipartFile articleImage, Long menuId) throws IOException;

	public Article updateArticle(ItemDto.Update update, MultipartFile articleImage, Long id) throws IOException;
}
