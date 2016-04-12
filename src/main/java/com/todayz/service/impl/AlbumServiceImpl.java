package com.todayz.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.todayz.domain.club.Club;
import com.todayz.domain.common.Image;
import com.todayz.domain.item.PhotoAlbum;
import com.todayz.service.AlbumService;
import com.todayz.service.ClubService;
import com.todayz.service.ImageService;
import com.todayz.service.ItemService;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private ItemService<PhotoAlbum> itemService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ClubService clubService;

	@Override
	public List<PhotoAlbum> create(Long clubId, MultipartFile[] photo) throws IOException {
		List<Image> images = null;
		List<PhotoAlbum> albums = new ArrayList<>();
		images = imageService.uploadImages(photo);

		for (Image image : images) {
			PhotoAlbum newPhotoAlbum = new PhotoAlbum();
			newPhotoAlbum.setPhoto(image);
			Club parent = clubService.getClub(clubId);
			newPhotoAlbum.setParentClub(parent);
			newPhotoAlbum = itemService.create(newPhotoAlbum);// create(create);
			albums.add(newPhotoAlbum);
		}
		return albums;
	}
}
