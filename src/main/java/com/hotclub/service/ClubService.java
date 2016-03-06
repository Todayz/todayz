package com.hotclub.service;

import com.hotclub.controller.support.ClubDto;
import com.hotclub.domain.club.Club;

public interface ClubService {

	public Club create(ClubDto.Create dto);

	public Club update(Long id, ClubDto.Update dto);

	public void delete(Long id);

	public Club getClub(Long id);
}