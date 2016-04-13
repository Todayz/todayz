package com.todayz.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private ItemService<PhotoAlbum> itemService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ClubService clubService;

	@Override
	public List<PhotoAlbum> createAlbum(Long clubId, MultipartFile[] photo) throws IOException {
		List<Image> images = null;
		List<PhotoAlbum> albums = new ArrayList<>();
		images = imageService.uploadImages(photo);

		for (Image image : images) {
			PhotoAlbum newPhotoAlbum = new PhotoAlbum();
			newPhotoAlbum.setPhoto(image);
			Club parent = clubService.getClub(clubId);
			newPhotoAlbum.setParentClub(parent);
			newPhotoAlbum = itemService.create(newPhotoAlbum);
			albums.add(newPhotoAlbum);
		}

		log.info("success create albums");
		return albums;
	}

	@Override
	public PhotoAlbum updateAlbum(Long id, MultipartFile photo) throws IOException {
		PhotoAlbum updatedPhotoAlbum = new PhotoAlbum();

		if (photo != null) {
			Image image = null;
			image = imageService.uploadImage(photo);
			updatedPhotoAlbum.setPhoto(image);
		}

		PhotoAlbum album = itemService.getItem(id);

		// update 메소드에서 할 수 있는 방법이 없는지 고민 필요.
		if (updatedPhotoAlbum.getPhoto() != null) {
			album.setPhoto(updatedPhotoAlbum.getPhoto());
		}
		album.setUpdatedDate(new Date());

		log.info("success update album");
		return album;
	}
}
