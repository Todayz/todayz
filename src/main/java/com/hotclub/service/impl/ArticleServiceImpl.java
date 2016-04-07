package com.hotclub.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hotclub.domain.item.Article;
import com.hotclub.domain.member.Member;
//import com.hotclub.exception.ClubNotFoundException;
import com.hotclub.repository.ItemRepository;
import com.hotclub.service.ItemService;
import com.hotclub.service.MemberService;
import com.hotclub.service.TodayzAclService;

@Service
@Transactional
public class ArticleServiceImpl implements ItemService<Article> {

	@Autowired
	private ItemRepository<Article> itemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MemberService memberService;

	// @Autowired
	// private MutableAclService mutableAclService;

	@Autowired
	private TodayzAclService<Article> todayzAclService;

	protected String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}

	@Override
	public Article create(Article article) {
		String authName = getUsername();
		Member writer = memberService.getMemberByAuthName(authName);

		Date now = new Date();
		article.setCreatedDate(now);
		article.setUpdatedDate(now);

		article.setWriter(writer);
		article = itemRepository.save(article);

		todayzAclService.addPermission(article, new PrincipalSid(authName), BasePermission.ADMINISTRATION);
		return article;
	}

	@Override
	public Article update(Long id, Article article) {
		Article updateArticle = getItem(id);
		updateArticle.setTitle(article.getTitle());
		updateArticle.setContent(article.getContent());
		if (article.getArticleImage() != null) {
			updateArticle.setArticleImage(article.getArticleImage());
		}
		updateArticle.setUpdatedDate(new Date());
		return updateArticle;
	}

	@Override
	public void delete(Long id) {
		Article article = getItem(id);
		itemRepository.delete(article);
		todayzAclService.deleteAcl(article);
	}

	@Override
	public Article getItem(Long id) {
		Article article = itemRepository.findOne(id);
		// if (article == null) {
		// throw new ClubNotFoundException(id);
		// }
		return article;
	}
}
