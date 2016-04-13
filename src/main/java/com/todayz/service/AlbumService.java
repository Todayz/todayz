package com.todayz.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todayz.domain.item.PhotoAlbum;

public interface AlbumService {
	public List<PhotoAlbum> createAlbum(Long clubId, MultipartFile[] photo) throws IOException;

	public PhotoAlbum updateAlbum(Long id, MultipartFile photo) throws IOException;
}
