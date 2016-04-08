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

import com.hotclub.domain.item.Item;
import com.hotclub.domain.member.Member;
//import com.hotclub.exception.ClubNotFoundException;
import com.hotclub.repository.ItemRepository;
import com.hotclub.service.ItemService;
import com.hotclub.service.MemberService;
import com.hotclub.service.TodayzAclService;

@Service
@Transactional
public class ItemServiceImpl<T extends Item> implements ItemService<T> {

	@Autowired
	private ItemRepository<T> itemRepository;

	@Autowired
	private ModelMapper modelMapper;

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
		return item;
	}

	@Override
	public T update(Long id, T item) {
		T updateArticle = getItem(id);
		/* //modelMapper.
		 updateArticle.setTitle(item.getTitle());
		 updateArticle.setContent(item.getContent());
		 if (item.getArticleImage() != null) {
		 updateArticle.setArticleImage(item.getArticleImage());
		 }
		updateArticle.setUpdatedDate(new Date());*/
		return updateArticle;
	}

	@Override
	public void delete(Long id) {
		T item = getItem(id);
		itemRepository.delete(item);
		todayzAclService.deleteAcl(item);
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
