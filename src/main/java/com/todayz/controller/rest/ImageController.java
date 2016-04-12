package com.todayz.controller.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todayz.controller.support.MemberDto;
import com.todayz.domain.common.Image;
import com.todayz.service.ImageService;

@RestController
@RequestMapping("/upload")
@SuppressWarnings("rawtypes")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private Environment env;

	@RequestMapping(value = "/images", method = POST)
	public ResponseEntity uploadFile(@RequestPart("properties") @Valid MemberDto.Create create,
			@RequestParam("profileImage") MultipartFile profileImage) {

		//TODO 업로드 시 이미지 크기 줄일 수 있도록.
		Image image = null;
		try {
			image = imageService.uploadImage(profileImage);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(image, HttpStatus.OK);
	} // method uploadFile

	/**
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/images/{id}", method = GET)
	public void getImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Image image = imageService.getImage(id);
		String directory = env.getProperty("todayz.home.images");
		String filepath = Paths.get(directory, image.getImageName()).toString();
		InputStream in = new FileInputStream(filepath);
		response.setContentType(image.getContentType());
		IOUtils.copyLarge(in, response.getOutputStream());
	}
}
