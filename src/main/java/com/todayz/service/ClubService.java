package com.todayz.service;

import com.todayz.controller.support.ClubDto;
import com.todayz.domain.club.Club;

public interface ClubService {

	public Club create(ClubDto.Create dto);

	public Club update(Long id, ClubDto.Update dto);

	public void delete(Long id);

	public Club getClub(Long id);
}