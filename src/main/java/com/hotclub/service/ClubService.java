package com.hotclub.service;

import com.hotclub.domain.club.Club;

public interface ClubService {

	public Club create(Club club);

	public void update(Club club);

	public void delete(Long id);
}