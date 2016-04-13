package com.todayz.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.todayz.controller.support.ClubDto;
import com.todayz.domain.club.Club;

public interface ClubService {

	public Club create(ClubDto.Create dto, MultipartFile mainImage) throws IOException;

	public Club update(Long id, ClubDto.Update dto, MultipartFile mainImage)  throws IOException;

	public void delete(Long id);

	public Club getClub(Long id);
}