package com.todayz.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.todayz.domain.common.Image;

public interface ImageService {
	public Image uploadImage(MultipartFile profileImage) throws IOException;

	public Image uploadImage(Long id, MultipartFile profileImage) throws IOException;

	public Image getImage(Long id);
	
	public void remove(Long id);
}
