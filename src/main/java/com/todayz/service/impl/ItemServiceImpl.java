package com.todayz.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.todayz.domain.item.Item;
import com.todayz.domain.member.Member;
import com.todayz.repository.ItemRepository;
import com.todayz.service.ItemService;
import com.todayz.service.MemberService;
import com.todayz.service.TodayzAclService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ItemServiceImpl<T extends Item> implements ItemService<T> {

	@Autowired
	private ItemRepository<T> itemRepository;

	// @Autowired
	// private ModelMapper modelMapper;

	@Autowired
	private MemberService memberService;

	// @Autowired
	// private MutableAclService mutableAclService;

	@Autowired
	private TodayzAclService<T> todayzAclService;

	protected String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}

	@Override
	public T create(T item) {
		String authName = getUsername();
		Member writer = memberService.getMemberByAuthName(authName);

		Date now = new Date();
		item.setCreatedDate(now);
		item.setUpdatedDate(now);

		item.setWriter(writer);
		item = itemRepository.save(item);

		todayzAclService.addPermission(item, new PrincipalSid(authName), BasePermission.ADMINISTRATION);

		log.info("item create success");
		return item;
	}

	@Override
	public T update(Long id, T item) {
		T updatedItem = getItem(id);
		/*
		 * //modelMapper. updateArticle.setTitle(item.getTitle());
		 * updateArticle.setContent(item.getContent()); if
		 * (item.getArticleImage() != null) {
		 * updateArticle.setArticleImage(item.getArticleImage()); }
		 * updateArticle.setUpdatedDate(new Date());
		 */
		log.info("item update success");
		return updatedItem;
	}

	@Override
	public void delete(Long id) {
		T item = getItem(id);
		itemRepository.delete(item);
		todayzAclService.deleteAcl(item);

		log.info("item delete success");
	}

	@Override
	public T getItem(Long id) {
		T item = itemRepository.findOne(id);
		// if (item == null) {
		// throw new ClubNotFoundException(id);
		// }
		return item;
	}
}
