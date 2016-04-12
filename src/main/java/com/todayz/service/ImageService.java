package com.todayz.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todayz.domain.common.Image;

public interface ImageService {
	public Image uploadImage(MultipartFile image) throws IOException;
	
	public List<Image> uploadImages(MultipartFile[] images) throws IOException;

	public Image uploadImage(Long id, MultipartFile image) throws IOException;

	public Image getImage(Long id);
	
	public void remove(Long id);
}
