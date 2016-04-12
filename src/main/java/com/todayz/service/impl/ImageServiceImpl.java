package com.todayz.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.todayz.domain.common.Image;
import com.todayz.repository.ImageRepository;
import com.todayz.service.ImageService;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private Environment env;

	@Override
	public Image uploadImage(MultipartFile image) throws IOException {
		return uploadImage(null, image);
	}

	@Override
	public List<Image> uploadImages(MultipartFile[] images) throws IOException {
		List<Image> uploadImages = new ArrayList<>();
		for (MultipartFile image : images) {
			uploadImages.add(uploadImage(null, image));
		}
		return uploadImages;
	}

	@Override
	public Image uploadImage(Long id, MultipartFile image) throws IOException {
		if (id != null) {
			remove(id);
		}
		Image uploadImage = null;
		// Get the filename and build the local file path
		String filename = image.getOriginalFilename();
		filename = getStoreFileName(filename);
		String directory = env.getProperty("todayz.home.images");
		String filepath = Paths.get(directory, filename).toString();

		// Save the file locally
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		stream.write(image.getBytes());
		stream.close();

		uploadImage = new Image();
		uploadImage.setImageName(filename);
		uploadImage.setContentType(image.getContentType());

		uploadImage = imageRepository.save(uploadImage);
		return uploadImage;
	}

	@Override
	public Image getImage(Long id) {
		Image image = imageRepository.getOne(id);
		return image;
	}

	@Override
	public void remove(Long id) {
		if (id == null) {
			throw new NullPointerException();
		}
		imageRepository.delete(id);
	}

	/**
	 * @param originalFilename
	 * @return
	 */
	protected String getStoreFileName(String originalFilename) {
		if (StringUtils.isNotBlank(originalFilename)) {
			String fileType = StringUtils.substringAfterLast(originalFilename, ".");
			String filename = StringUtils.substringBeforeLast(originalFilename, ".");
			StringBuilder storeFile = new StringBuilder();
			storeFile.append(filename);
			storeFile.append("_");
			storeFile.append(System.currentTimeMillis());
			storeFile.append(".");
			storeFile.append(fileType);
			return storeFile.toString();
		}
		return null;
	}
}
