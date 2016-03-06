package com.hotclub.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotclub.controller.support.ClubDto;
import com.hotclub.domain.club.Club;
import com.hotclub.exception.ClubNotFoundException;
import com.hotclub.repository.ClubRepository;
import com.hotclub.service.ClubService;

@Service
@Transactional
public class ClubServiceImpl implements ClubService {

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Club create(ClubDto.Create dto) {
		Club club = modelMapper.map(dto, Club.class);

		Date now = new Date();
		club.setCreatedDate(now);
		club.setUpdatedDate(now);

		return clubRepository.save(club);
	}

	@Override
	public Club update(Long id, ClubDto.Update dto) {
		// TODO Auto-generated method stub

		Club club = getClub(id);
		club.setTitle(dto.getTitle());
		club.setMainImage(dto.getMainImage());
		club.setNotice(dto.getNotice());

		club.setUpdatedDate(new Date());
		return null;
	}

	@Override
	public void delete(Long id) {
		clubRepository.delete(getClub(id));
	}

	@Override
	public Club getClub(Long id) {
		Club club = clubRepository.findOne(id);
		if (club == null) {
			throw new ClubNotFoundException(id);
		}
		return club;
	}
}
